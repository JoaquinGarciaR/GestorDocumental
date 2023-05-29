package com.msi.gestordocumental.controllers;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.msi.gestordocumental.entities.CoAuthorPermission;
import com.msi.gestordocumental.entities.Departament;
import com.msi.gestordocumental.entities.Office;
import com.msi.gestordocumental.entities.ReaderPermission;
import com.msi.gestordocumental.entities.Unit;
import com.msi.gestordocumental.entities.User;
import com.msi.gestordocumental.exceptions.UnAuthorizedException;
import com.msi.gestordocumental.responses.AuthorResponse;
import com.msi.gestordocumental.responses.DepartamentName;
import com.msi.gestordocumental.responses.Properties;
import com.msi.gestordocumental.responses.UnitName;
import com.msi.gestordocumental.responses.accessResponse;
import com.msi.gestordocumental.services.CoAuthorPermissionService;
import com.msi.gestordocumental.services.DepartamentService;
import com.msi.gestordocumental.services.FileService;
import com.msi.gestordocumental.services.OfficeService;
import com.msi.gestordocumental.services.ReaderPermissionService;
import com.msi.gestordocumental.services.UnitService;
import com.msi.gestordocumental.services.UserService;
/* import com.msi.gestordocumental.services.VersionService;

import ch.qos.logback.core.joran.conditional.ElseAction; */

@RequestMapping(value = "/files")
@RestController
public class FileController {
    // COMENTARIO
    @Autowired
    private FileService service;
    @Autowired
    private DepartamentService departmentService;
    @Autowired
    private OfficeService Officeservice;
    @Autowired
    private UnitService unitService;
    @Autowired
    private ReaderPermissionService ReadersService;
    @Autowired
    private CoAuthorPermissionService CoAuthorService;
    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public void UploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userID") String userID)
            throws Exception {
        String name = file.getOriginalFilename();
        Integer count = Officeservice.getCount(name); // Cuenta las versiones
        if (count > 0) { // Si ya hay una version anterior
            newVersion(file, userID, count);
        } else {
            NewOffice(file, userID, count);
        }
    }

    public void NewOffice(MultipartFile file, String userID, Integer count) throws Exception {
        Office o = new Office();
        o.setName(file.getOriginalFilename());
        o.setState(true); // Valid
        o.setDateCreated(null);
        o.setType(service.getFileExtension(file.getOriginalFilename())); // gets file extension
        User user = userService.getUser(userID); // User
        o.setLastUpdated(null);
        o.setVersion(1);// Sino, se establece la version 1
        o.setAuthor(user); // Se establece como autor el que sube la version 1
        o.setLastModifier(user); // El ultimo que lo modifica
        Integer idDep = userService.getUserDepartment(userID);
        Departament d = departmentService.getDepartament(idDep);
        o.setDepartament(d);
        Integer idUnit = userService.getUserUnit(userID);
        Unit u = unitService.getUnit(idUnit);
        o.setUnit(u);
        o.setValid(false);
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension.equals("pdf") == true || extension == "PDF" || extension == "Pdf") {
            File convFile = new File(file.getOriginalFilename()); // Se crea un file temporal
            // convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile); // Con los bytes del file
            fos.write(file.getBytes());
            fos.close();
            // File convFile = new File(file.getOriginalFilename()); // Se crea un file
            // temporal
            if (convFile != null) {

                if (call(convFile) == true) { // Se verifican firmas

                    o.setValid(true);
                }
            }
            convFile.delete();
        }
        o.setData(file.getBytes()); // Guarda los bytes del archivo
        Officeservice.saveOffice(o);

        if (user.getBoss()) {
            readers(false, false, new ArrayList<String>(), o.getIdOffice());
            coAuthors(false, false, new ArrayList<String>(), o.getIdOffice());
        } else {
            readers(true, true, new ArrayList<String>(), o.getIdOffice());
            coAuthors(false, false, new ArrayList<String>(), o.getIdOffice());
        }
    }

    public void newVersion(MultipartFile file, String userID, Integer count) throws Exception {
        String name = file.getOriginalFilename();
        Office o = new Office();
        Integer lastVersionID = Officeservice.getLastVersionID(name, count);

        o.setName(file.getOriginalFilename());
        o.setState(true); // Valid
        o.setDateCreated(Officeservice.getOffice(lastVersionID).getDateCreated()); // Fecha de cuando se creo la primera
                                                                                   // version
        o.setType(service.getFileExtension(file.getOriginalFilename())); // gets file extension
        User user = userService.getUser(userID); // User
        User autor = userService.getUser(Officeservice.getOfficeAuthor(o.getName())); // User author
        if (CoAuthorService.editorPermission(lastVersionID, userID)
                || (Officeservice.getOfficeAuthor(name).equals(userID))) {
            o.setVersion(count + 1); // Se agrega una version nueva
            o.setAuthor(autor);

        } else {

            throw new UnAuthorizedException();
        }
        o.setLastModifier(user); // El ultimo que lo modifica
        Integer idDep = userService.getUserDepartment(autor.getIdUser());
        Departament d = departmentService.getDepartament(idDep);
        o.setDepartament(d);
        Integer idUnit = userService.getUserUnit(autor.getIdUser());
        Unit u = unitService.getUnit(idUnit);
        o.setUnit(u);
        o.setValid(false);
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension.equals("pdf") == true || extension == "PDF" || extension == "Pdf") {
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            if (convFile != null) {

                if (call(convFile) == true) { // Se verifican firmas

                    o.setValid(true);
                }
            }
            convFile.delete();
        }
        o.setData(file.getBytes()); // Guarda los bytes del archivo
        Officeservice.saveOffice(o);
        // Se busca el officio que se acaba de guardar
        Integer newAux = Officeservice.getLastVersionID(name, count + 1);
        Office auxOffice = Officeservice.getOffice(newAux);

        List<CoAuthorPermission> oldPermissions = CoAuthorService.getAllCoAuthorPermissionsbyOffice(lastVersionID);
        List<ReaderPermission> oldReadersPermissions = ReadersService.getAllReadersPermissionsbyOffice(lastVersionID);
        for (CoAuthorPermission coAuthorPermission : oldPermissions) {
            CoAuthorPermission aux = new CoAuthorPermission();
            aux.setDateCreated(coAuthorPermission.getDateCreated());
            aux.setDepartament(coAuthorPermission.getDepartament());
            aux.setUnit(coAuthorPermission.getUnit());
            aux.setUser(coAuthorPermission.getUser());
            aux.setOffice(auxOffice);
            CoAuthorService.saveCoAuthorPermission(aux);
        }
        for (ReaderPermission reader : oldReadersPermissions) {
            ReaderPermission aux = new ReaderPermission();
            aux.setDateCreated(reader.getDateCreated());
            aux.setDepartament(reader.getDepartament());
            aux.setUnit(reader.getUnit());
            aux.setUser(reader.getUser());
            aux.setOffice(auxOffice);
            ReadersService.saveReaderPermission(aux);
        }
    }

    @PostMapping("/setAccess")
    public void setFileAccess(@RequestBody accessResponse request) {
        readers(request.getAllDepartmentL(), request.getAllUnitsL(), request.getUsersL(), request.getFile_id());
        coAuthors(request.getAllDepartmentC(), request.getAllUnitsC(), request.getUsersC(), request.getFile_id());
    }


    public Boolean readers(Boolean allDep, Boolean allunits, List<String> readers, Integer id) {
        if (ReadersService.getCount(id) != 0) {
            ReadersService.deleteRegisters(id);
        }
        if (readers.size() != 0) {
            for (String aux : readers) {
                ReaderPermission rp = new ReaderPermission();
                Unit u = new Unit();
                Departament d = new Departament();
                Office of = Officeservice.getOffice(id);
                rp.setOffice(of);
                String idAuthor = of.getAuthor().getIdUser();
                if (allDep) {
                    Integer idDep = userService.getUserDepartment(idAuthor);
                    d = departmentService.getDepartament(idDep);
                } else {
                    d = null;
                }
                rp.setDepartament(d);
                if (allunits) {
                    Integer idUnit = userService.getUserUnit(idAuthor);
                    u = unitService.getUnit(idUnit); // Unidad del usuario
                } else {
                    u = null;
                }
                rp.setUnit(u);
                User user = userService.getUser(aux);
                rp.setUser(user);
                ReadersService.saveReaderPermission(rp);
            }
        } else {
            ReaderPermission rp = new ReaderPermission();
            Unit u = new Unit();
            Departament d = new Departament();
            Office of = Officeservice.getOffice(id);
            rp.setOffice(of);
            String idAuthor = of.getAuthor().getIdUser();
            if (allDep) {
                Integer idDep = userService.getUserDepartment(idAuthor);
                d = departmentService.getDepartament(idDep);
            } else {
                d = null;
            }
            rp.setDepartament(d);
            if (allunits) {
                Integer idUnit = userService.getUserUnit(idAuthor);
                u = unitService.getUnit(idUnit); // Unidad del usuario
            } else {
                u = null;
            }
            rp.setUnit(u);
            ReadersService.saveReaderPermission(rp);
        }
        return true;
    }

    public Boolean coAuthors(Boolean allDep, Boolean allunits, List<String> coauthors, Integer id) {
        if (CoAuthorService.getCount(id) != 0) {
            CoAuthorService.deleteRegisters(id);
        }
        if (coauthors.size() != 0) {
            for (String aux : coauthors) {
                CoAuthorPermission cp = new CoAuthorPermission();
                Office of = Officeservice.getOffice(id);
                String idAuthor = of.getAuthor().getIdUser();
                Unit u = new Unit();
                Departament d = new Departament();
                cp.setOffice(of);
                if (allDep) {
                    Integer idDep = userService.getUserDepartment(idAuthor);
                    d = departmentService.getDepartament(idDep);
                } else {
                    d = null;
                }
                cp.setDepartament(d);
                if (allunits) {
                    Integer idUnit = userService.getUserUnit(idAuthor);
                    u = unitService.getUnit(idUnit); // Unidad del usuario
                } else {
                    u = null;
                }
                cp.setUnit(u);
                User user = userService.getUser(aux);
                cp.setUser(user);
                CoAuthorService.saveCoAuthorPermission(cp);
            }
        } else {
            CoAuthorPermission cp = new CoAuthorPermission();
            Office of = Officeservice.getOffice(id);
            String idAuthor = of.getAuthor().getIdUser();
            Unit u = new Unit();
            Departament d = new Departament();
            cp.setOffice(of);
            if (allDep) {
                Integer idDep = userService.getUserDepartment(idAuthor);
                d = departmentService.getDepartament(idDep);
            } else {
                d = null;
            }
            cp.setDepartament(d);
            if (allunits) {
                Integer idUnit = userService.getUserUnit(idAuthor);
                u = unitService.getUnit(idUnit); // Unidad del usuario
            } else {
                u = null;
            }
            cp.setUnit(u);
            CoAuthorService.saveCoAuthorPermission(cp);
        }
        return true;
    }

    @GetMapping("/getAccess/{file_id}")
    public accessResponse getCoauthors(@PathVariable("file_id") Integer id) {
        accessResponse response = new accessResponse();
        List<String> usersC = new ArrayList<String>();
        List<String> usersL = new ArrayList<String>();
        if (CoAuthorService.getCoAuthorList(id) != null) {
            usersC = CoAuthorService.getCoAuthorList(id);
            if (usersC.get(0) == null) {
                usersC.clear();
            }
        }
        if (ReadersService.getReadersList(id) != null) {
            usersL = ReadersService.getReadersList(id);
            if (usersL.get(0) == null) {
                usersL.clear();
            }
        }
        List<String> aux = usersL; // Auxiliar list for Readers
        List<String> aux2 = usersC; // Auxiliar list for coAuthors
        Office of = Officeservice.getOffice(id);
        if (ReadersService.getAllDepartments(id) != null) { // Si todos los de mi departamento son lectores
            response.setAllDepartmentL(true); // setear a verdadero
            usersL.addAll(userService.getAllUsersbyDepartment(of.getDepartament().getIdDepartament()));
        } else {
            response.setAllDepartmentL(false);
        }
        if (ReadersService.getUnits(id) != null) { // Si todos los de mi unidad son lectores
            response.setAllUnitsL(true);
            usersL.addAll(userService.getAllUsersbyUnit(of.getUnit().getIdUnit()));

        } else {
            response.setAllUnitsL(false);
        }
        if (CoAuthorService.getAllDepartments(id) != null) { // Si todos los de mi departamento son coautores
            response.setAllDepartmentC(true); // setear a verdadero
            usersC.addAll(userService.getAllUsersbyDepartment(of.getDepartament().getIdDepartament()));
        } else {
            response.setAllDepartmentC(false);
        }
        if (CoAuthorService.getUnits(id) != null) { // Si todos los de mi unidad son coautores
            response.setAllUnitsC(true);
            usersC.addAll(userService.getAllUsersbyUnit(of.getUnit().getIdUnit()));
        } else {
            response.setAllUnitsC(false);
        }
        aux = deleteDuplicates(usersL);
        aux2 = deleteDuplicates(usersC);
        aux.remove(of.getAuthor().getIdUser()); // Eliminamos autor de la lista
        aux2.remove(of.getAuthor().getIdUser()); // Eliminamos autor de los coautores
        response.setUsersC(aux2);
        response.setUsersL(aux);
        return response;
    }

    private List<String> deleteDuplicates(List<String> list) {
        List<String> newList = new ArrayList<String>();
        for (String id : list) {
            if (!newList.contains(id)) {
                newList.add(id);
            }
        }
        return newList;
    }

    @PostMapping("/properties")
    public Properties getFilProperties(@RequestBody Map<String, String> requestbody) {
        String idUser = requestbody.get("idUser");
        Integer idDoc = Integer.parseInt(requestbody.get("idDoc"));
        String permission = "";


        Properties properties = new Properties();
        Office office = Officeservice.getOffice(idDoc);
        if (office != null) {
            properties.setDateCreated(office.getDateCreated());
            properties.setLastUpdated(office.getLastUpdated());
            properties.setName(office.getName());
            properties.setType(office.getType());
            properties.setVersion(office.getVersion());
            properties.setState(office.getState());
            properties.setValid(office.getValid());
            User aux = office.getAuthor();
            AuthorResponse author = new AuthorResponse(aux.getName(), aux.getLastName1(), aux.getLastName2());
            properties.setAuthor(author);
            properties.setUnit(new UnitName(office.getUnit().getName()));
            properties.setDepartament(new DepartamentName(office.getDepartament().getName()));
            if (aux.getIdUser().equals(idUser)) {
                permission = "autor";
            } else {
                List<String> usersEditores = new ArrayList<String>();
                Integer firstV = Officeservice.getFirstVersionID(office.getName());
                usersEditores = CoAuthorService.getCoAuthorList(firstV);
                if (usersEditores.contains(idUser)) {
                    permission = "editor";
                } else {
                    permission = "lector";
                }
            }
            properties.setPermissions(permission);
        }
        return properties;
    }

    // Metodos de la firma
    private byte[] getByteRangeData(ByteArrayInputStream bis, int[] byteRange) {
        int length1 = byteRange[1] + byteRange[3];
        byte[] contentSigned = new byte[length1];
        bis.skip(byteRange[0]);
        bis.read(contentSigned, 0, byteRange[1]);
        bis.skip(byteRange[2] - byteRange[1] - byteRange[0]);
        bis.read(contentSigned, byteRange[1], byteRange[3]);
        bis.reset();
        return contentSigned;

    }

    public Boolean call(File pdfFile) throws Exception {
        if (pdfFile == null) {

            return false;
        }

        PDDocument pdfDoc = null;
        Boolean var = false;
        try {

            ByteArrayInputStream pdfBytes = new ByteArrayInputStream(
                    Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath())));

            pdfDoc = PDDocument.load(pdfFile);

            pdfDoc.getSignatureDictionaries().forEach(a -> {
                try {
                    processSignature(a, pdfBytes);

                } catch (Exception e) {
                   

                }
            });

            for (int indice = 0; indice < pdfDoc.getSignatureDictionaries().size(); indice++) {
                if (processSignature(pdfDoc.getSignatureDictionaries().get(indice), pdfBytes) != false) {
                    return true;
                }
            }
            pdfBytes.close();
            return var;
        } finally {
            if (pdfDoc != null) {
                pdfDoc.close();
            }
        }
    }

    private Boolean processSignature(PDSignature signature,
            ByteArrayInputStream pdfBytes) throws Exception {
        Boolean var = false;
        byte[] contentToSigned = getByteRangeData(pdfBytes, signature.getByteRange());
        String filter = signature.getFilter();
        String subFilter = signature.getSubFilter();
        String reason = Optional.ofNullable(signature.getReason()).orElse("N/A");

        if (!filter.trim().equalsIgnoreCase("Adobe.PPKLite")) {

            return false;
        }
        if (!subFilter.trim().contains("ETSI.CAdES.detached") &&
                !subFilter.trim().contains("adbe.pkcs7.detached") &&
                !subFilter.trim().contains("ETSI.RFC3161")) {

            return false;
        }


        // Get PKCS#7 Data
        CMSSignedData signedData = new CMSSignedData(signature.getContents());
        // Get SignerInfo
        SignerInformation signerInfo = signedData.getSignerInfos().iterator().next();

        // Get Attribute
        Attribute attribute1 = signerInfo.getSignedAttributes().get(PKCSObjectIdentifiers.pkcs_9_at_messageDigest);
        Attribute attribute2 = null;
        if (signerInfo.getUnsignedAttributes() != null) {
            attribute2 = signerInfo.getUnsignedAttributes().get(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken);
        }

        // Get MD in CMS
        String messageDigest = "";
        if (subFilter.contains("ETSI.RFC3161")) {
            TimeStampToken timeToken = new TimeStampToken(signedData);
            messageDigest = Base64.getEncoder().encodeToString(
                    timeToken.getTimeStampInfo().getMessageImprintDigest());
        } else {
            messageDigest = Base64.getEncoder().encodeToString(
                    Hex.decode(attribute1.getAttributeValues()[0].toString().substring(1)));
        }
        MessageDigest digest = MessageDigest.getInstance(signerInfo.getDigestAlgOID());


        String signatureSID = signerInfo.getSID().getSerialNumber().toString(16);
        // Check timestamp token
        if (attribute2 != null && attribute2.getAttributeValues().length > 0) {

        }

        // Getting PublicKey
        Collection<X509CertificateHolder> matches = signedData.getCertificates().getMatches(signerInfo.getSID());
        byte[] pubByte = matches.iterator().next().getSubjectPublicKeyInfo().getEncoded();

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubByte);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey pubKey = kf.generatePublic(keySpec);

        // Check signature
        String encAlgo = null;
        if (signerInfo.getEncryptionAlgOID().trim().equals("1.2.840.113549.1.1.1")) {
            encAlgo = "RSA";
        }
        if (encAlgo != null) {
            if (digest.getAlgorithm().equals("1.3.14.3.2.26")) {
                encAlgo = "SHA1withRSA";
            } else if (digest.getAlgorithm().equals("2.16.840.1.101.3.4.2.1")) {
                encAlgo = "SHA256withRSA";
            } else if (digest.getAlgorithm().equals("2.16.840.1.101.3.4.2.2")) {
                encAlgo = "SHA384withRSA";
            } else if (digest.getAlgorithm().equals("2.16.840.1.101.3.4.2.3")) {
                encAlgo = "SHA512withRSA";
            }
        } else {
            encAlgo = signerInfo.getEncryptionAlgOID();
        }
        Signature rsaSign = Signature.getInstance(encAlgo);
        rsaSign.initVerify(pubKey);
        rsaSign.update(signerInfo.getEncodedSignedAttributes());
        boolean cmsSignatureValid = rsaSign.verify(signerInfo.getSignature());

        if (cmsSignatureValid) {

            var = true;
        } else {

            return false;
        }

        // Calculate MD in PDF

        String mdPdf = Base64.getEncoder().encodeToString(digest.digest(contentToSigned));


        if (mdPdf.equals(messageDigest)) {

            var = true;

        } else {

            return false;
        }
        return var;

    }

    

    private static File buscar(String archivoABuscar, File directorio) {

        File[] archivos = directorio.listFiles();

        for (File archivo : archivos) {

            if (archivo.getName().equals(archivoABuscar)) {

                return archivo;
            }
            if (archivo.isDirectory()) {
                File resultadoRecursion = buscar(archivoABuscar, archivo);
                if (resultadoRecursion != null) {

                    return resultadoRecursion;
                }
            }
        }

        return null;
    }

    @GetMapping("/getVersions/{file_name}")
    public List<Office> findOfficeById(@PathVariable("file_name") String name) {
        return Officeservice.getAllVersions(name);
    }

    @GetMapping("/getCertificatexx/{name}/{version}")
    private ResponseEntity<byte[]> PDFCertificatexx(@PathVariable("name") String name,
            @PathVariable("version") Integer version) throws Exception {

        List<Office> list = Officeservice.getAllVersions(name);
        Office of = list.get(list.size() - 1);

        for (Office o : list) {
            if (o.getVersion() == version) {
                of = o;
            }

        }
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        // 2. Create PdfWriter
        PdfWriter.getInstance(document, new FileOutputStream("C:\\GestorDocumental\\files\\Report" + of.getName()));

        // 3. Open document
        document.open();

        // 4. Add content
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.UNDERLINE);
        Paragraph title = new Paragraph("Municipalidad de San Isidro", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
        Paragraph subtitle = new Paragraph("Reporte de Firmas Digitales", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        Paragraph paragraph1 = new Paragraph("");
        Paragraph paragraph2 = new Paragraph("");

        // Agregar los párrafos al documento
        document.add(paragraph1);
        document.add(paragraph2);

        // Agregar imagen del logo
        Image logo = Image.getInstance(
                "src/main/resources/municipalidad_logo.png"); // Ruta de la imagen del logo
        logo.setAlignment(Element.ALIGN_CENTER);
        logo.scaleToFit(100, 100); // Ajustar tamaño de la imagen según tus necesidades
        document.add(logo);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph1);
        document.add(paragraph2);
        // Crear tabla con información adicional
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        table.addCell("Autor de la firma:");
        table.addCell(of.getAuthor().getName()+" "+of.getAuthor().getLastName1()+" "+of.getAuthor().getLastName2());

        table.addCell("Cedula:");
        table.addCell(of.getAuthor().getIdUser());

        table.addCell("Departamento:");
        table.addCell(of.getDepartament().getName());

        table.addCell("Día de creación:");
        table.addCell(of.getDateCreated().toString());

        table.addCell("Estado de las firmas:");
        if (of.getValid()) {
            table.addCell("Firmas Válidas");
        } else {
            table.addCell("Contiene Firmas Inválidas o No Contiene Firmas");
        }

        LocalDate todaysDate = LocalDate.now();
        table.addCell("Fecha del certificado:");
        table.addCell(todaysDate.toString());

        document.add(table);

        // 5. Close document
        document.close();

        // Obtener los datos del archivo PDF
        Path filePath = Paths.get("C:\\GestorDocumental\\files\\Report" + of.getName());
        byte[] data = Files.readAllBytes(filePath);

        File pdfFile = filePath.toFile();
        pdfFile.delete();



        // Configurar las cabeceras HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(of.getName()).build());
        headers.setContentLength(data.length);

        // Devolver el archivo PDF como un arreglo de bytes con las cabeceras HTTP
        // configuradas

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @GetMapping("/visualizeFile/{file_id}")
    public ResponseEntity<byte[]> getFileBytes(@PathVariable("file_id") Integer id) throws IOException {
        Office of = Officeservice.getOffice(id);

        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(of.getName()).build());

        return new ResponseEntity<byte[]>(of.getData(), headers, HttpStatus.OK);

    }

    @GetMapping("/visualizeFilexx/{file_id}")
    public ResponseEntity<byte[]> getFileBytesXX(@PathVariable("file_id") Integer id) throws IOException {
        Office of = Officeservice.getOffice(id);

        String formato = of.getType();

        HttpHeaders headers = new HttpHeaders();
        if (formato.equals("pdf")) {
            headers.setContentType(MediaType.APPLICATION_PDF);
        }
        if (formato.equals("jpg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
        if (formato.equals("doc")) {
            headers.setContentType(MediaType.valueOf("application/msword"));
        }
        if (formato.equals("docx")) {
            headers.setContentType(
                    MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        }

        return new ResponseEntity<byte[]>(of.getData(), headers, HttpStatus.OK);
    }

}
