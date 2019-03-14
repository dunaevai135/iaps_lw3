
let POINT_AREA_SIZE = {
    x: 218,
    y: 218
};
let POINT_SIZE_PX = 10;
let R_TO_PX = 80;

function getMouseAbsolutePosition(e) {
    let ev = e || window.event; //Moz || IE
    let x, y;

    if (ev.pageX) { //Moz
        x = ev.pageX;
        y = ev.pageY;
    }

    return {x, y};
}

function processClick(mouse_abs_pos, img_document_pos) {
    let realpos = {
        x: (mouse_abs_pos.x - img_document_pos.left - POINT_AREA_SIZE.x / 2) / R_TO_PX * CURRENT_R,
        y: -(mouse_abs_pos.y - img_document_pos.top - POINT_AREA_SIZE.y / 2) / R_TO_PX * CURRENT_R,
    };

    $("#form\\:x_text").val(realpos.x);
    $("#form\\:y_text").val(realpos.y);

    $("#form\\:save_button").click();
}

$(document).ready(function () {
    $("#areas").click(function (e) {
        let mouse_abs_pos = getMouseAbsolutePosition(e);
        let img_document_pos = $('#areas').offset();

        if (mouse_abs_pos.x < img_document_pos.left + POINT_AREA_SIZE.x
            && mouse_abs_pos.y < img_document_pos.top + POINT_AREA_SIZE.y) {
            processClick(mouse_abs_pos, img_document_pos);
        }
    });

    let slider = $("#form\\:r_slider");
    slider.bind("slide", function (e, ui) {
        CURRENT_R = ui.value;
        updateAreaCanvas(dots);
    });
});

function updateAreaCanvas(dots) {
    let canvas = document.getElementById("areas");
    let ctx = canvas.getContext("2d");

    let background = new Image();
    background.src = areasImageLink;
    background.onload = function () {
        ctx.drawImage(background, 0, 0);

        let green = "rgba(0, 200, 0";
        let red = "rgba(255, 0, 0";
        let darkgreen = "rgba(0, 100, 0";
        let darkred = "rgba(100, 0, 0";

        if (dots) {
            for (let dot of dots) {
                let realpos = {
                    x: dot.x / CURRENT_R * R_TO_PX + POINT_AREA_SIZE.x / 2,
                    y: -dot.y / CURRENT_R * R_TO_PX + POINT_AREA_SIZE.y / 2
                };

                ctx.beginPath();
                ctx.arc(realpos.x, realpos.y, POINT_SIZE_PX / 2, 0, 2 * Math.PI);

                let opacity = (Math.abs(dot.r - CURRENT_R) < Number.EPSILON) ? ", 1.0)" : ", 0.3)";

                ctx.fillStyle = (dot.result) ? green + opacity : red + opacity;
                ctx.fill();
                ctx.lineWidth = 2;
                ctx.strokeStyle = (dot.result) ? darkgreen + opacity: darkred + opacity;
                ctx.stroke();
            }
        }
    };
}
