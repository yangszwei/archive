const links = document.querySelectorAll('.link');
const linkImage = document.getElementById('link-image');
const linkBMI = document.getElementById('link-bmi');
const linkMT = document.getElementById('link-mt');
const tabImage = document.getElementById('image');
const tabBMI = document.getElementById('bmi');
const tabMT = document.getElementById('mt');
const bmiHeight = document.getElementById('height');
const bmiWeight = document.getElementById('weight');
const bmiSubmit = document.getElementById('bmi-submit');
const bmiResult = document.getElementById('bmi-result');
const bmiCategory = document.getElementById('bmi-cat');
const mtBody = document.getElementById('mt-body');
const photo = document.getElementById('photo');
const nextPicture = document.getElementById('next-picture');

if (location.hash === '#image') {
    linkImage.classList.add('active');
    tabImage.classList.remove('hidden');
} else if (location.hash === '#mt') {
    linkMT.classList.add('active');
    tabMT.classList.remove('hidden');
} else {
    linkBMI.classList.add('active');
    tabBMI.classList.remove('hidden');
}

for (const link of links) {
    link.addEventListener('click', (e) => {
        e.preventDefault();
        location.hash = e.target.getAttribute('href');
        for (const link of links) {
            const id = link.getAttribute('href').substring(1);
            link.classList.remove('active');
            document.getElementById(id).classList.add('hidden');
        }
        e.target.classList.add('active');
        const id = e.target.getAttribute('href').substring(1);
        document.getElementById(id).classList.remove('hidden');
    })
}

bmiSubmit.addEventListener('click', (e) => {
    e.preventDefault();
    const height = parseInt(bmiHeight.value, 10);
    const weight = parseInt(bmiWeight.value, 10);
    if (!height || !weight) return;
    const bmi = weight / (height * height) * 10000;
    bmiResult.value = bmi.toFixed(2).toString();
    bmiCategory.innerText = bmi < 18.5 ? '體重過輕' : bmi >= 23 ? '體重過重' : '正常體位';
    bmiCategory.style.color = bmi > 18.5 && bmi < 23 ? 'green' : 'red';
});

for (let i = 1; i < 10; ++i) {
    const tr = document.createElement('tr');
    for (let j = 1; j < 10; ++j) {
        const td = document.createElement('td');
        td.innerHTML = `${j} * ${i} = <b>${i * j}</b>`;
        tr.appendChild(td);
    }
    mtBody.appendChild(tr);
}

mtBody.addEventListener('mouseover', (e) => {
    let target = e.target;
    if (target.tagName === 'B') target = target.parentElement;
    let [i, j] = /(\d+) \* (\d+) =/.exec(target.innerHTML).slice(1);
    // add "active" class to same row and column
    for (const td of mtBody.querySelectorAll('td')) {
        const [i2, j2] = /(\d+) \* (\d+) =/.exec(td.innerHTML).slice(1);
        td.classList[i === i2 || j === j2 ? 'add' : 'remove']('active');
    }
})

mtBody.addEventListener('mouseout', (e) => {
    for (const td of mtBody.querySelectorAll('td')) {
        td.classList.remove('active');
    }
});

function colorMT() {
    for (const b of mtBody.querySelectorAll('b')) {
        b.style.color = ['red', 'green', 'blue', 'orange', 'purple', 'pink', 'black'][Math.floor(Math.random() * 7)];
    }
}

colorMT();

mtBody.addEventListener('click', colorMT);

nextPicture.addEventListener('click', (e) => {
    nextPicture.disabled = true;
    nextPicture.innerText = '載入中...';
    photo.src = `https://picsum.photos/200/300?random=${Math.random()}`;
});

let isFirstImage = true;

photo.onload = (e) => {
    if (isFirstImage) {
        isFirstImage = false;
        return;
    }
    let threshold = 3;
    const timer = setInterval(() => {
        if (threshold === 0) {
            nextPicture.innerText = '下一張圖片';
            nextPicture.disabled = false;
            clearInterval(timer);
        } else {
            nextPicture.innerText = `請稍候 ${threshold} 秒`;
            threshold--;
        }
    }, 1000);
}
