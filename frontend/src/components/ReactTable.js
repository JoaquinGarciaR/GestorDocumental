import React, { useMemo } from 'react';
import { useTable, useSortBy, useGlobalFilter } from 'react-table';
import { Table, Row, Col, Form, InputGroup } from 'react-bootstrap';

function ReactTable({ columns, data }) {

    const memoizedColumns = useMemo(() => columns, [columns]);
    const memoizedData = useMemo(() => data, [data]);

    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow, setGlobalFilter
    } = useTable({
        columns: memoizedColumns,
        data: memoizedData
    }, useGlobalFilter, useSortBy);

    const handleFilterChange = (e) => {
        const value = e.target.value || undefined;
        setGlobalFilter(value);
    };

    return (
        <>
            <Row className="mb-2">
                <Col lg="5">
                    <InputGroup className="mb-3" onChange={handleFilterChange}>
                        <Form.Control placeholder="Palabra clave" type="text" />
                        <InputGroup.Text><i className="fa fa-search" ></i></InputGroup.Text>
                    </InputGroup>
                </Col>
            </Row>
            <Table {...getTableProps()}>
                <thead>
                    {headerGroups.map((headerGroup) => (
                        <tr {...headerGroup.getHeaderGroupProps()}>
                            {headerGroup.headers.map((column) => (
                                <th className="header-dark" {...column.getHeaderProps(column.getSortByToggleProps())} title="">
                                    {column.id === 'link' && <i className="fa fa-gear" style={{ marginRight: "5px" }}></i>}
                                    {column.id !== 'link' && column.render('Header')}
                                    {column.id !== 'link' &&
                                        <span>
                                            {column.id !== 'Ult. Modificacion' && column.isSorted
                                                ? column.isSortedDesc
                                                    ? ' 🔼'
                                                    : ' 🔽'
                                                : ''}
                                            {column.id === 'Ult. Modificacion' && column.isSorted
                                                ? column.isSortedDesc
                                                    ? ' 🔽'
                                                    : ' 🔼'
                                                : ''}
                                        </span>
                                    }
                                </th>
                            ))}
                        </tr>
                    ))}
                </thead>
                <tbody {...getTableBodyProps()}>

                    {rows.map((row) => {
                        prepareRow(row);
                        return (
                            <tr {...row.getRowProps()}>
                                {row.cells.map((cell) => (
                                    <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
                                ))}
                            </tr>
                        );
                    })}
                </tbody>
            </Table>
        </>
    );
}

export default ReactTable;