var config = require('../nightwatch.conf.js');

module.exports = {
  'Recruiter Login 1' : function (browser) {
    browser
      .url('http://35.162.177.133:8090/aes/login')
      .waitForElementVisible('body', 1000)
      .setValue('input[id=input-email]', 'nickolas.jurczak@revature.com')
	    .setValue('input[id=input-password]', 'password')
      .waitForElementVisible('input[value=login]', 1000)
      .click('input[value=login]')
      .pause(3000)
      .assert.title('AES | Recruiter Home')
      .end();
  }
};
