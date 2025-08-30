// JavaScript Document

$(document).ready(function () {
    var intputElements = document.getElementsByTagName("INPUT");
    for (var i = 0; i < intputElements.length; i++) {
        intputElements[i].oninvalid = function (e) {
            e.target.setCustomValidity("");
            if (!e.target.validity.valid) {
                if (e.target.name == "username") {
                    e.target.setCustomValidity("The field 'Username' cannot be left blank");
                }
                else {
                    e.target.setCustomValidity("The field 'Password' cannot be left blank");
                }
            }
        };
    }
})


function abrir(url) {
    open(url, '', 'top=300,left=300,width=300,height=300');
}

function format(input) {
    var num = input.value.replace(/\./g, '');
    if (!isNaN(num)) {
        num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g, '$1.');
        num = num.split('').reverse().join('').replace(/^[\.]/, '');
        input.value = num;
    }
    else {
        input.value = input.value.replace(/[^\d\.]*/g, '');
    }
}
