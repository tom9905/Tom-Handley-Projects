const typingText = document.querySelector(".typing-text p"),
inpField = document.querySelector(".wrapper .input-field"),
tryAgainBtn = document.querySelector(".content button"),
timeTag = document.querySelector(".time span b"),
mistakeTag = document.querySelector(".mistake span"),
wpmTag = document.querySelector(".wpm span"),
cpmTag = document.querySelector(".cpm span");
let timer,
maxTime = 30,
timeLeft = maxTime,
charIndex = mistakes = isTyping = 0;
function loadParagraph() {
    const ranIndex = Math.floor(Math.random() * paragraphs.length);
    typingText.innerHTML = "";
    paragraphs[ranIndex].split("").forEach(char => {
        let span = `<span>${char}</span>`
        typingText.innerHTML += span;
    });
    typingText.querySelectorAll("span")[0].classList.add("active");
    document.addEventListener("keydown", () => inpField.focus());
    typingText.addEventListener("click", () => inpField.focus());
}
function initTyping() {
    let characters = typingText.querySelectorAll("span");
    let typedChar = inpField.value.split("")[charIndex];
    if(charIndex < characters.length - 1 && timeLeft > 0) {
        if(!isTyping) {
            timer = setInterval(initTimer, 1000);
            isTyping = true;
        }
        if(typedChar == null) {
            if(charIndex > 0) {
                charIndex--;
                if(characters[charIndex].classList.contains("incorrect")) {
                    mistakes--;
                }
                characters[charIndex].classList.remove("correct", "incorrect");
            }
        } else {
            if(characters[charIndex].innerText == typedChar) {
                characters[charIndex].classList.add("correct");
            } else {
                mistakes++;
                characters[charIndex].classList.add("incorrect");
            }
            charIndex++;
        }
        characters.forEach(span => span.classList.remove("active"));
        characters[charIndex].classList.add("active");
        let wpm = Math.round(((charIndex - mistakes)  / 5) / (maxTime - timeLeft) * 30);
        wpm = wpm < 0 || !wpm || wpm === Infinity ? 0 : wpm;
        
        wpmTag.innerText = wpm;
        mistakeTag.innerText = mistakes;
        cpmTag.innerText = charIndex - mistakes;
    } else {
        clearInterval(timer);
        inpField.value = "";
    }   
}
function initTimer() {
    if (timeLeft > 0) {
        timeLeft--;
        timeTag.innerText = timeLeft;

        updateChart(); // Call the updateChart function

        let wpm = Math.round(((charIndex - mistakes) / 5) / (maxTime - timeLeft) * 30);
        wpmTag.innerText = wpm;
    } else {
        clearInterval(timer);
    }
}
function resetGame() {
    loadParagraph();
    clearInterval(timer);
    timeLeft = maxTime;
    charIndex = mistakes = isTyping = 0;
    inpField.value = "";
    timeTag.innerText = timeLeft;
    wpmTag.innerText = 0;
    mistakeTag.innerText = 0;
    cpmTag.innerText = 0;
}
loadParagraph();
inpField.addEventListener("input", initTyping);
tryAgainBtn.addEventListener("click", resetGame);


const ctx = document.getElementById('typingChart').getContext('2d');
const typingChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [], // Add labels dynamically as the test progresses
        datasets: [{
            label: 'Words Per Minute (WPM)',
            borderColor: '#17A2B8',
            backgroundColor: 'rgba(23, 162, 184, 0.2)',
            data: [], // Add WPM data dynamically as the test progresses
        }],
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                type: 'linear',
                position: 'bottom',
            },
            y: {
                min: 0,
            },
        },
    },
});

function updateChart() {
    const timeElapsed = maxTime - timeLeft;
    const wpm = Math.round(((charIndex - mistakes) / 5) / timeElapsed * 30);

    // Determine the color based on whether a mistake was made
    const color = mistakes > 0 ? 'red' : '#17A2B8';

    typingChart.data.labels.push(timeElapsed);
    typingChart.data.datasets[0].data.push({
        x: timeElapsed,
        y: wpm,
        color: color,
    });

    // Update the chart
    typingChart.update();
}

function resetGame() {
    loadParagraph();
    clearInterval(timer);
    timeLeft = maxTime;
    charIndex = mistakes = isTyping = 0;
    inpField.value = "";
    timeTag.innerText = timeLeft;
    wpmTag.innerText = 0;
    mistakeTag.innerText = 0;
    cpmTag.innerText = 0;

    // Clear old chart data
    typingChart.data.labels = [];
    typingChart.data.datasets[0].data = [];

    // Update the chart
    typingChart.update();
}