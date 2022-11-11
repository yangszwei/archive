const $ = i => Array.from(document.getElementsByName(i));
$.sel = i => document.querySelector(i);
const nameList = [ "帳號", "密碼", "性別", "喜歡的科目", "最討厭的科目" ];
const dataList = [ "username", "password", "sex", "fav", "nfav" ];
let prev = $.sel("select").value;

function main () {
    (function c (i) {
        let name = dataList[i], ins = $(name);
        if (name) {
            if (!ins[1] && ins[0].value) {
                c(i + 1); return !1;
            } else {
                for (e of ins) if (e.checked) { c(i + 1); return !1; };
            } notify(`未正確填寫${nameList[dataList.indexOf(name)]}！`);
        } else {
            dataList.forEach(i => {
                let el = $.sel(`[data-name='${i}']`), src= $(i);
                if (!src[1]) {
                    el.innerHTML = src[0].value;
                } else if (i == "fav") {
                    el.innerHTML = "";
                    for (e of src) if (e.checked) { el.innerHTML += e.value + " "; };
                } else {
                    for (e of src) if (e.checked) { el.innerHTML = e.value; };
                }
            });
            $.sel("s-modal").className = "active";
        }
    })(0);
}

function notify (message) {
    let card = document.createElement("div");
    card.style.animationDuration = "3000ms", card.innerHTML += message;
    card.innerHTML += `<button onclick="this.parentElement.outerHTML=''">
        <i class="fa fa-times"></i>
    </button>`;
    $.sel("s-notify").appendChild(card);
    setTimeout(() => {
        if ($.sel("s-notify").firstChild) {
            $.sel("s-notify").removeChild($.sel("s-notify").firstChild);
        }
    }, 3000);
}

function filter () {
    let s_order = [ "國文", "英文", "數學", "電子學", "數位邏輯", "程式設計" ],
        current = [], flag = !1, addItem = data =>
        ($.sel("#nfavs").innerHTML += `<option value="${data}">${data}</option>`);
    $.sel("select").value && (prev = $.sel("select").value);
    $.sel("#nfavs").innerHTML = `<option value="" selected disabled>請選擇</option>`;
    addItem("無");
    s_order.forEach(each => {
        if (!$.sel(`input[name=fav][value='${each}']`).checked) {
            current.push(each);
            addItem(each);
            (each == prev) && (flag = !0);
        }
    });
    prev == "無" && (flag = !0);
    flag && ($.sel("#nfavs").value = prev);
}

$("fav").forEach(i => i.addEventListener("click", filter));
$.sel(".modal").addEventListener("click", e => e.stopPropagation());
setTimeout(console.clear);