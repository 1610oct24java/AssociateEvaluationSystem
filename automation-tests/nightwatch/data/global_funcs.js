module.exports = {
	recruiterLogin: function (browser, email, password) {
      browser
        .url('http://35.162.177.133:8090/aes/login')
        .waitForElementVisible('body', 1000)
        .setValue('input[id=input-email]', email)
  	  	.setValue('input[id=input-password]', password)
        .waitForElementVisible('input[value=login]', 1000)
        .click('input[value=login]')
        .pause(3000);
    },
	registerCandidate: function (browser, firstName, lastName, email) {
		browser
		  .click('a[href=recruit]')
          .waitForElementVisible('input[id=inputFirstName]', 1000)
	      .assert.title('AES | Register Candidate')
		  .setValue('input[id=inputFirstName]', firstName)
		  .setValue('input[id=inputLastName]', lastName)
		  .setValue('input[id=inputEmail]', email)
		  //.click('select[id=courseSelect]')
          //.waitForElementVisible('option[value=option:3]', 1000)
		  //.click('option[value=option:3]')
		  //.click('input[id=btn-register-recruit]');
	}
};
