
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

// Sidebar click logic
const navItems = document.querySelectorAll('.sidebar .nav-item a');
const mainContent = document.querySelector('.banking-main');
const sidebar = document.querySelector('.sidebar');

navItems.forEach(item => {
  item.addEventListener('click', e => {
    e.preventDefault();

    // Remove highlight from other navs
    navItems.forEach(nav => nav.parentElement.classList.remove('active'));
    item.parentElement.classList.add('active');

    // Get icon type (for example purpose)
    const icon = item.querySelector('i').classList[1]; // get second class like 'fa-house'

    // 🏠 If home icon clicked → show full dashboard
    if (icon === 'fa-house') {
      sidebar.style.display = 'block';
      mainContent.style.display = 'block';
      mainContent.innerHTML = `
         <h1 style="text-align:center; margin-top:50px;">🏠 Welcome back to your Dashboard</h1>
      `
      ;
    } 
     else if (icon === 'fa-headset') {
      sidebar.style.display = 'block';
      mainContent.style.display = '';
      mainContent.innerHTML = `
        <h1 style="text-align:center; margin-top:50px;">Welcome to Customer Care </h1>
      `;
    } 
     else if (icon === 'fa-chart-line') {
      sidebar.style.display = 'block';
      mainContent.style.display = '';
      mainContent.innerHTML = `
        <h1 style="text-align:center; margin-top:50px;">Welcome to Invesement  </h1>
      `;
    } 
       else if (icon === 'fa-exchange-alt') {
      sidebar.style.display = 'block';
      mainContent.style.display = '';
      mainContent.innerHTML = `
        <h1 style="text-align:center; margin-top:50px;">Transition </h1>
      `;
    }
       else if (icon === 'fa-user') {
      sidebar.style.display = 'block';
      mainContent.style.display = '';
      mainContent.innerHTML = `
        <h1 style="text-align:center; margin-top:50px;">User Information </h1>
      `;
    }
      else if (icon === 'fa-file-invoice-dollar') {
      sidebar.style.display = 'block';
      mainContent.style.display = '';
      mainContent.innerHTML = `
                    <div class="transfer-container">
    <h2>Initiate a New Transfer</h2>

    <form id="transferForm">
        <div class="form-group">
            <label for="sourceAccount">From Account:</label>
            <select id="sourceAccount" name="sourceAccount" required>
                <option value="">Select Account</option>
                <option value="savings">Savings Account (₹ 28,901.23)</option>
                <option value="checking">Checking Account (₹ 12,345.67)</option>
                </select>
        </div>

        <div class="form-group">
            <label for="destinationAccount">To Account Number/Beneficiary:</label>
            <input type="text" id="destinationAccount" name="destinationAccount" placeholder="Enter 12-digit account number" required>
        </div>

        <div class="form-group">
            <label for="amount">Amount (₹):</label>
            <input type="number" id="amount" name="amount" min="1" step="0.01" required>
        </div>

        <div class="form-group">
            <label for="remarks">Remarks (Optional):</label>
            <input type="text" id="remarks" name="remarks" maxlength="50">
        </div>

        <button type="submit" class="btn-primary">Confirm Transfer</button>
    </form>

    <div id="transferStatus" style="margin-top: 20px;"></div>
</div>
      `;
    }
    // 📄 Else → hide main content & sidebar
    else {
      sidebar.style.display = 'block';
      mainContent.innerHTML = `
        <h2 style="text-align:center; margin-top:50px; color:#333;">Page content coming soon...</h2>
      `;
    }
  });
});

















document.getElementById('transferForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Stop the default form submission

    const sourceAccount = document.getElementById('sourceAccount').value;
    const destinationAccount = document.getElementById('destinationAccount').value;
    const amount = parseFloat(document.getElementById('amount').value);
    const transferStatus = document.getElementById('transferStatus');

    // --- 1. Basic Front-End Validation ---
    if (!sourceAccount || !destinationAccount || isNaN(amount) || amount <= 0) {
        transferStatus.innerHTML = '<p style="color: red;">Please ensure all fields are correctly filled and the amount is valid.</p>';
        return;
    }

    // --- 2. Gather Data (for a real app, this would go to the backend) ---
    const transferDetails = {
        from: sourceAccount,
        to: destinationAccount,
        amount: amount,
        remarks: document.getElementById('remarks').value
    };

    console.log("Transfer Data to be sent:", transferDetails);

    // --- 3. Simulate Successful Transfer Status ---
    transferStatus.innerHTML = '<p style="color: green; font-weight: bold;">✅ Transfer of ₹' + amount.toFixed(2) + ' initiated successfully!</p>';
    // You might clear the form here:
    document.getElementById('transferForm').reset();
    
    // In a real application, you'd use 'fetch()' or 'XMLHttpRequest' here to contact the server.
    /*
    fetch('/api/transfer', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(transferDetails)
    })
    .then(response => response.json())
    .then(data => {
        // Update status based on server response
    })
    .catch(error => {
        // Handle network errors
    });
    */
});


