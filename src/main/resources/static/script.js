document.getElementById('phoneForm').addEventListener('submit', function (event) {
    event.preventDefault();
    const phoneNumber = document.getElementById('phoneNumber').value;
    const resultElement = document.getElementById('countryName');
    resultElement.textContent = '';
    if (!phoneNumber) {
        resultElement.textContent = 'Please enter your phone number.';
        return;
    }
    const phoneNumberPattern = /^\d{10,15}$/;
    if (!phoneNumberPattern.test(phoneNumber)) {
        resultElement.textContent = 'Please provide a valid phone number with 10 to 15 digits.';
        return;
    }
    console.log('Submitting phone number:', phoneNumber);
    const requestBody = JSON.stringify({phoneNumber});
    fetch('http://localhost:8088/api/v1/phone/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: requestBody
    })
        .then(response => {
            console.log('Network response status:', response.status);
            if (!response.ok) {
                throw new Error('Unable to process request. Please try again later.');
            }
            return response.json();
        })
        .then(data => {
            console.log('Response data:', data);
            const countries = data?.country;
            if (Array.isArray(countries) && countries.length > 0) {
                resultElement.textContent = `Countries: ${countries.join(', ')}`;
            } else {
                resultElement.textContent = 'No country information available for the provided phone number.';
            }
        })
        .catch(error => {
            console.error('Request error:', error);

            resultElement.textContent = 'An error occurred: ' + error.message;
        });
});

document.getElementById('phoneNumber').addEventListener('input', function (event) {
    const inputElement = event.target;
    const resultElement = document.getElementById('countryName');
    const phoneNumberPattern = /^[0-9]*$/;
    if (!phoneNumberPattern.test(inputElement.value)) {
        resultElement.textContent = 'Please enter a valid phone number using digits only.';
        inputElement.value = inputElement.value.replace(/[^0-9]/g, '');
    } else {
        resultElement.textContent = '';
    }
});