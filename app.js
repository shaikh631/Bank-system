
const amountRange = document.getElementById("amountrange");
const amountInput = document.getElementById("amountInput");
const emiText = document.getElementById("emi");
const interestText = document.getElementById("interest");
const rateInput = document.getElementById("rate");
let duration = 6;

// Set Duration Function
function setDuration(months, event) {
  duration = months;
  document.querySelectorAll(".duration button").forEach(btn => btn.classList.remove("active"));
  event.target.classList.add("active");
  calculate();
}

// EMI Calculation
function calculate() {
  let principal = parseInt(amountInput.value);
  let annualRate = parseFloat(rateInput.value);
  let monthlyRate = annualRate / 12 / 100;

  let emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, duration)) /
            (Math.pow(1 + monthlyRate, duration) - 1);

  let totalPayment = emi * duration;
  let interest = totalPayment - principal;

  emiText.innerHTML = `<i class="fa-solid fa-indian-rupee-sign"></i> ${emi.toFixed(0)}`;
  interestText.innerHTML = `Total Interest: <i class="fa-solid fa-indian-rupee-sign"></i>${interest.toFixed(0)}`;
}

// Sync slider & input
amountRange.addEventListener("input", () => {
  amountInput.value = amountRange.value;
  calculate();
});

amountInput.addEventListener("input", () => {
  amountRange.value = amountInput.value;
  calculate();
});

rateInput.addEventListener("input", calculate);

calculate(); // initial

// Open Calculator on button click
document.getElementById("openCalc").addEventListener("click", () => {
  document.getElementById("loanCalc").style.display = "flex";
});




// Mode
// 🌗 Dark Mode Toggle
const modeToggle = document.querySelector('.Mode');
const body = document.body;

// Check if user has a saved theme
if (localStorage.getItem('theme') === 'dark') {
  body.classList.add('dark-mode');
  modeToggle.innerHTML = '<i class="fa-solid fa-sun"></i>'; // show sun icon
}

// Toggle on click
modeToggle.addEventListener('click', () => {
  body.classList.toggle('dark-mode');

  // Save user preference
  if (body.classList.contains('dark-mode')) {
    localStorage.setItem('theme', 'dark');
    modeToggle.innerHTML = '<i class="fa-solid fa-sun"></i>';
  } else {
    localStorage.setItem('theme', 'light');
    modeToggle.innerHTML = '<i class="fa-solid fa-moon"></i>';
  }
});
