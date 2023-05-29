export const parseDate = (date) => {
    var d = new Date(date);
    var min = "00"
    if(d.getMinutes() === 0){
        min = "00"
    }
    if(d.getMinutes() < 10){
        min = "0" + d.getMinutes()
    }
    else{
        min = d.getMinutes()
    }
    return (
        [d.getMonth() + 1, d.getDate(), d.getFullYear()].join("/") +
        " " +
        [d.getHours(), min].join(":")
    );
};