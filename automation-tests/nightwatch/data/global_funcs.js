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
  logout: function (browser) {
    browser
      .click('button[class=\'btn-revature\']')
      .pause(3000);
  }
};
