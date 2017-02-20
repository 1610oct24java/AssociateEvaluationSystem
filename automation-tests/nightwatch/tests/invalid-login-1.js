var config = require('../nightwatch.conf.js');

module.exports = {
  'Invalid Login 1' : function (browser) {
    browser
      .url('http://35.162.177.133:8090/aes/login')
      .waitForElementVisible('body', 1000)
      .setValue('input[id=input-email]', 'lsdkjfhwelkfhewlk')
	  .setValue('input[id=input-password]', 'lsdkjfhwelkfhewlk')
      .waitForElementVisible('input[value=login]', 1000)
      .click('input[value=login]')
      .pause(3000)
      .end();
  }
};
