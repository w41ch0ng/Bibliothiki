/*!
* Start Bootstrap - Freelancer v7.0.5 (https://startbootstrap.com/theme/freelancer)
* Copyright 2013-2021 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-freelancer/blob/master/LICENSE)
*/
//
// Scripts
// 

// Bootstrap code for navbar functionality

// Wait for the DOM content to be fully loaded before running the script
window.addEventListener('DOMContentLoaded', event => {

    // Function to add or remove 'navbar-shrink' class based on scroll position
    var navbarShrink = function () {
        // Select navbar element by its ID
        const navbarCollapsible = document.body.querySelector('#mainNav');
        
        // If navbar element is not found, exit function
        if (!navbarCollapsible) {
            return;
        }
        
        // Check if page is scrolled to top
        if (window.scrollY === 0) {
            // Remove 'navbar-shrink' class if at top
            navbarCollapsible.classList.remove('navbar-shrink')
        } else {
            // Add 'navbar-shrink' class if scrolled down
            navbarCollapsible.classList.add('navbar-shrink')
        }

    };
 
    // Run navbarShrink function once on page load
    navbarShrink();

    // Add event listener to run navbarShrink function on scroll
    document.addEventListener('scroll', navbarShrink);

    // Initialise ScrollSpy for main navigation bar
    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            offset: 72,
        });
    };

    // Handle closing of responsive navbar menu when item is clicked
    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = [].slice.call(
        document.querySelectorAll('#navbarResponsive .nav-link')
    );
    responsiveNavItems.map(function (responsiveNavItem) {
        responsiveNavItem.addEventListener('click', () => {
            // Check if navbar toggle button is visible
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                // Simulate click on navbar toggler to close menu
                navbarToggler.click();
            }
        });
    });

});
