var config = require('../nightwatch.conf.js');

module.exports = {
  'Recruiter Navigate to Register Candidate' : function(browser) {
    var data = browser.globals;
    data.recruiterLogin(browser, 'nickolas.jurczak@revature.com', 'password');
    browser
    .assert.title('AES | Recruiter Home')
    .waitForElementVisible('a[href=recruit]', 2000)
    .click('a[href=recruit]')
    .pause(1000)
    .assert.title('AES | Register Candidate')
    .assert.visible('#inputFirstName')
    .assert.visible('#inputLastName')
    .assert.visible('#inputEmail')
    .assert.visible('#courseSelect')
    .assert.visible('#btn-register-recruit')
    .assert.visible('a[href=view]')
    .assert.visible('#logout')
    .end();
  },

  'Recruiter Navigate back to view Candidate' : function(browser) {
    var data = browser.globals;
    data.recruiterLogin(browser, 'nickolas.jurczak@revature.com', 'password');
    browser
    .assert.title('AES | Recruiter Home')
    .waitForElementVisible('a[href=recruit]', 2000)
    .click('a[href=recruit]')
    .pause(1000)
    .assert.title('AES | Register Candidate')
    .assert.visible('a[href=view]')
    .click('a[href=view]')
    .pause(1000)
    .assert.title('AES | Recruiter Home')
    .end();
  }
}
