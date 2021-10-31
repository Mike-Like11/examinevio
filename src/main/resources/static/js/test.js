var sec = 90;
function pad ( val ) {
    return val < 0 || val>10 ? val : "0" + val;
}
setInterval( function(){
    $("#seconds").html(pad(--sec%60));
    $("#minutes").html(pad(parseInt(sec/60,10)));
}, 1000);