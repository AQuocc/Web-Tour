// Form Validation
document.addEventListener('DOMContentLoaded', function() {
    // Apply validation to all forms with needs-validation class
    const forms = document.querySelectorAll('.needs-validation');
    Array.from(forms).forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });

    // Date input validation for tour booking
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    
    if (startDateInput && endDateInput) {
        endDateInput.addEventListener('change', function() {
            const startDate = new Date(startDateInput.value);
            const endDate = new Date(this.value);
            
            if (endDate < startDate) {
                this.setCustomValidity('End date must be after start date');
            } else {
                this.setCustomValidity('');
            }
        });
    }

    // Dynamic price calculation for booking
    const participantsInput = document.getElementById('numberOfParticipants');
    const pricePerPerson = document.getElementById('pricePerPerson');
    const totalPriceDisplay = document.getElementById('totalPrice');
    
    if (participantsInput && pricePerPerson && totalPriceDisplay) {
        participantsInput.addEventListener('input', function() {
            const price = parseFloat(pricePerPerson.value);
            const participants = parseInt(this.value) || 0;
            const total = price * participants;
            totalPriceDisplay.textContent = `$${total.toFixed(2)}`;
        });
    }
});

// Auto-dismiss alerts
const alerts = document.querySelectorAll('.alert-dismissible');
alerts.forEach(alert => {
    setTimeout(() => {
        const closeButton = alert.querySelector('.btn-close');
        if (closeButton) {
            closeButton.click();
        }
    }, 5000);
});

// Image preview for tour form
function previewImage(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        const preview = document.getElementById('imagePreview');
        
        reader.onload = function(e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
        }
        
        reader.readAsDataURL(input.files[0]);
    }
}

// Booking confirmation
function confirmBooking() {
    return confirm('Are you sure you want to proceed with this booking?');
}

// Tour deletion confirmation
function confirmDelete(tourId) {
    const modal = document.getElementById('deleteModal');
    if (modal) {
        const deleteForm = modal.querySelector('#deleteForm');
        deleteForm.action = `/tours/admin/delete/${tourId}`;
        
        const bootstrapModal = new bootstrap.Modal(modal);
        bootstrapModal.show();
    }
}

// Search form enhancement
const searchForm = document.querySelector('.search-section form');
if (searchForm) {
    const clearButton = document.createElement('button');
    clearButton.type = 'button';
    clearButton.className = 'btn btn-secondary';
    clearButton.textContent = 'Clear';
    clearButton.onclick = function() {
        searchForm.reset();
        searchForm.submit();
    };
    
    const submitButton = searchForm.querySelector('[type="submit"]');
    if (submitButton) {
        submitButton.parentNode.insertBefore(clearButton, submitButton.nextSibling);
    }
}

// Scroll to top button
window.onscroll = function() {
    const scrollButton = document.getElementById('scrollTopBtn');
    if (scrollButton) {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            scrollButton.style.display = 'block';
        } else {
            scrollButton.style.display = 'none';
        }
    }
};

function scrollToTop() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}