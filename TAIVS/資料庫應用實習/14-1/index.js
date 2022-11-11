const $ = i => document.querySelector(i);
$.all = i => document.querySelectorAll(i);

Object.defineProperty(NodeList.prototype, "forEach", {
        get: function () { return Array.from(this).forEach; }
    });

if (localStorage.ran) {
    $('#tutorial').style.display='none';
} else {
    localStorage.ran = 1;
}

function sort(query, target) {
    setTimeout(() => {
        target.style.animation = "";
    }, 1000);
    let xhr = new XMLHttpRequest;
    xhr.open("POST", `source.php`, 1);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.readyState == 4 && xhr.status == '200') {
            $("#amt-body").innerHTML = xhr.responseText;
        setTimeout(()=>{$.all("[placeholder]").forEach(i => freeze(i));});
            Sortable.create($("#amt-body"), {
                handle: ".cover",
                filter: '.disabled',
                onMove: event => {
                    return !event.related.classList.contains('disabled');
                },
                preventOnFilter: false
            });
            $("#at-action .loader").classList.add("spin");
            setTimeout(() => {
                $.all("[placeholder]").forEach(i => freeze(i));
                $("#at-action .loader").classList.remove("spin");
            }, 1400);
        }
    };
    xhr.send(`sort=${query}&type=${target.getAttribute("data-next")}`);
    if (target.getAttribute("data-next") == "ASC") {
        target.setAttribute("data-next", "DESC");
        target.querySelector("i").className = "fas fa-sort-down";
    } else {
        target.setAttribute("data-next", "ASC");
        target.querySelector("i").className = "fas fa-sort-up";
    }

}

function fetch() {
    return new Promise(function (resolve, reject) {
        let xhr = new XMLHttpRequest;
        xhr.open("GET", `source.php`, 1);
        xhr.onload = function () {
            if (xhr.readyState == 4 && xhr.status == "200") {
                resolve(xhr.responseText);
            }
        };
        xhr.onerror = e => reject(e);
        xhr.send();
    });
}

function row(target) {
    if (target.classList.contains("active")) {
        target.classList.remove("active");
    } else {
        if ($(".cover.active"))
            $(".cover.active").classList.remove("active");
        target.classList.add("active");
    }
}

function select(el) {
    event.stopPropagation();
    if (el.parentElement.parentElement.classList.contains("selected"))
        el.parentElement.parentElement.classList.remove("selected")
    else
        el.parentElement.parentElement.classList.add("selected")
    if ($(".cover.selected + .edit .identity .seatNo")) {
        $("#at-action .select_all").classList.remove("hidden");
        $("#at-action .trash").classList.remove("hidden");
    } else {
        $("#at-action .select_all").classList.add("hidden");
        $("#at-action .trash").classList.add("hidden");
    }
}

function select_all() {
    let flag = 0;
    Array.from(document.querySelectorAll("#amt-body .row:not(.disabled):not(.empty) .cover")).forEach(i => {
        if (!i.classList.contains("selected")) {
            flag = 1;
        }
    });
    Array.from(document.querySelectorAll("#amt-body .row:not(.disabled):not(.empty) .cover")).forEach(i => {
        if (flag) {
            i.classList.add("selected")
        } else {
            i.classList.remove("selected")
        }
    });
    if ($(".cover.selected + .edit .identity .seatNo")) {
        $("#at-action .select_all").classList.remove("hidden");
        $("#at-action .trash").classList.remove("hidden");
    } else {
        $("#at-action .select_all").classList.add("hidden");
        $("#at-action .trash").classList.add("hidden");
    }
}

function load() {
    fetch().then(html => {
        $("#amt-body").innerHTML = html;
        Sortable.create($("#amt-body"), {
            handle: ".cover",
            filter: '.disabled',
            onMove: event => {
                return !event.related.classList.contains('disabled');
            },
            preventOnFilter: false
        });
        $.all("[placeholder]").forEach(i => freeze(i));
        $("#at-action .loader").classList.add("spin");
        setTimeout(() => {
            $("#at-action .loader").classList.remove("spin");
            $.all("[placeholder]").forEach(i => freeze(i));
        }, 1400);
    });
}

function post(target) {
    let xhr = new XMLHttpRequest;
    xhr.open("POST", `post.php`, 1);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.readyState == 4 && xhr.status == '200') {
            $("#at-action .select_all").classList.add("hidden");
            $("#at-action .trash").classList.add("hidden");
            load();
        }
    };
    let pos = target.querySelector(".identity .seatNo").innerHTML;
    let dat = {};
    let get = name => {
        let elem = target.querySelector("." + name);
        let value = elem.value,
            prev = elem.getAttribute("placeholder");
        if (name == "studentName") {
            dat[name] = value || prev;
        } else {
            dat[name] = (value && !Number.isNaN(Number(value)) && (Number(value) >= 0) && (Number(value) <= 100)) ? value : prev;
        }
    };

    get("studentName"), get("chinese"), get("english"), get("math"), get("pro1"), get("pro2");
    xhr.send(`action=update&seatNo=${pos}&studentName=${dat.studentName}&chinese=${dat.chinese}&english=${dat.english}&math=${dat.math}&pro1=${dat.pro1}&pro2=${dat.pro2}`);
}

function create(target) {
    let xhr = new XMLHttpRequest;
    xhr.open("POST", `post.php`, 1);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.readyState == 4 && xhr.status == '200') {
            load();
        }
    };
    let pos = target.querySelector(".identity .seatNo").innerHTML;
    let dat = {};
    let get = name => {
        let elem = target.querySelector("." + name);
        let value = elem.value;
        if (name == "studentName") {
            dat[name] = value || ("同學" + pos);
        } else {
            dat[name] = (value && !Number.isNaN(Number(value)) && (Number(value) >= 0) && (Number(value) <= 100)) ? value : 0;
        }
    };

    get("studentName"), get("chinese"), get("english"), get("math"), get("pro1"), get("pro2");
    xhr.send(`action=set&seatNo=${pos}&studentName=${dat.studentName}&chinese=${dat.chinese}&english=${dat.english}&math=${dat.math}&pro1=${dat.pro1}&pro2=${dat.pro2}`);
}

function random(target) {
    target.style.animation = "shake 1s infinite";
    setTimeout(() => {
        target.style.animation = "";
    }, 1000);
    let xhr = new XMLHttpRequest;
    xhr.open("POST", `post.php`, 1);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.readyState == 4 && xhr.status == '200') {
            load();
        }
    };
    xhr.send("action=random");
}

function removeMultiple() {
    $("#app-foot .state").style.width = 0;
    $("#app-foot").classList.add("active");
    let query = Array.from(document.querySelectorAll(".cover.selected + .edit .identity .seatNo"));
    (function remove(i) {
        let target = query[i].innerHTML;
        let xhr = new XMLHttpRequest;
        xhr.open("POST", `post.php`, 1);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onload = function () {
            if (xhr.readyState == 4 && xhr.status == '200') {
                $("#app-foot .state").style.width = `calc((100% / ${query.length}) * ${i + 1})`;
                if (i + 1 == query.length) {
                    $("#at-action .select_all").classList.add("hidden");
                    $("#at-action .trash").classList.add("hidden");
                    $("#app-foot").classList.remove("active");
                    load();
                } else {
                    remove(i + 1);
                }
            }
        };
        xhr.send(`action=delete&seatNo=${target}`);
    })(0);
}

function remove(target) {
    let xhr = new XMLHttpRequest;
    xhr.open("POST", `post.php`, 1);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.readyState == 4 && xhr.status == '200') {
            load();
        }
    };
    let pos = target.querySelector(".identity .seatNo").innerHTML;
    xhr.send(`action=delete&seatNo=${pos}`);
}

window.onload = function (e) {
    load();
};

function freeze (target) {
    let config = {
        attributes: true,
        attributeOldValue: true
    };
    freeze.observer.observe(target, config);
}

freeze.observer = new MutationObserver(function (mutations, observer) {
    let config = {
        attributes: true,
        attributeOldValue: true
    };
    observer.disconnect();
    mutations.forEach(function (mutation, i) {
        let target = mutations[i].target;
        if (mutation.attributeName == "placeholder") {
            target.setAttribute("placeholder", mutation.oldValue);
        }
    });
    $.all("[placeholder]").forEach(i => freeze(i));
});

function restore (el) {
    let xhr = new XMLHttpRequest;
    xhr.open("GET", `restore.php`, 1);
    xhr.onload = function () {
        if (xhr.readyState == 4 && xhr.status == '200') {
            load();
        }
    };
    xhr.send();
}