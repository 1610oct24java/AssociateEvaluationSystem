var config = require('../nightwatch.conf.js');

module.exports = {
  'Recruiter Login 1' : function (browser) {
	var data	= browser.globals;
	data.recruiterLogin(browser, 'nickolas.jurczak@revature.com', 'password');

    browser
      .assert.title('AES | Recruiter Home');

	//data.recruiterLogin(browser, 'willie.c.jensen@gmail.com', 'b51p5mdo');
	data.registerCandidate(browser, 'Billy', 'Bob', 'aes.candidate@gmail.com');

	browser.end();
  }
};
