// use JavaScript conf file so we can include comments and helper functions
module.exports = {
  "src_folders": [
    "tests"    // location of e2e tests
  ],
  "output_folder": "./reports", // reports (test outcome) output by nightwatch
  'globals_path': "./data/global_funcs.js",
  //'page_objects_path': 'page-objects',
  "selenium": {
    "start_process": true,
	  "server_path": "./selenium-server-standalone-3.1.0.jar",
    "port": 4444, // standard selenium port
    "cli_args": {
      "webdriver.gecko.driver" : "node_modules/.bin/geckodriver"
    }
  },
  "test_settings": {
    "default": {
      "selenium_port"  : 4444,
      "selenium_host"  : "localhost",
      "screenshots": {
        "enabled": false, // if you want to keep screenshots
        "path": './screenshots' // save screenshots here
      },
      "globals": {
        "waitForConditionTimeout": 10000, // time to wait for a condition
        'client': './data/dev.js'
      },
      "desiredCapabilities": { // default browser for tests
        "browserName": "firefox"
      }
    },
    "firefox": {
      "desiredCapabilities": {
        "browserName": "firefox",
        "javascriptEnabled": true // turn off to test progressive enhancement
      }
    },
    "firefox-windows": {
      "desiredCapabilities": {
        "browserName": "firefox",
        "javascriptEnabled": true // turn off to test progressive enhancement
	    },
	    "cli_args": {
	      "webdriver.gecko.driver": "node_modules/geckodriver/geckodriver.exe"
      }
	  },
    "chrome": {
      "desiredCapabilities": {
        "browserName": "chrome",
        "javascriptEnabled": true
      },
      "cli_args": {
        "webdriver.chrome.driver": "node_modules/chromedriver/lib/chromedriver/chromedriver.exe"  //Run "npm install chromedriver" for this to work 
      }
    },
    "edge": {
      "desiredCapabilities": {
        "browserName": "MicrosoftEdge",
        "javascriptEnabled": true
      },
      "cli_args": {
        //Download the driver at "https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/" and insert the path below
        "webdriver.edge.driver": "C:/Users/User/Documents/MicrosoftWebDriver.exe"  
      }
    },
    "ci-server-firefox" : {   // custom test environment that overwrites default settings when used as either '-e ci-server-firefox' or '--env ci-server-firefox'
      "integration" : true,
      "desiredCapabilities": {
        "browserName": "firefox",
        "marionette": true
	    },
	    "cli_args": {
        "webdriver.gecko.driver" : "geckodriver"
      }
    },
    'ci-server-chrome' : {
      "integration" : true,
      "desiredCapabilities": {
        "browserName": "chrome",
        "marionette": true
      }
    }
  }
}
