toroCam Camera Control
======================

toroCam is a camera control system, which gives you unparalleled control over your SLR though an open source app and a small, Arduino compatible board.

This repo hosts the Android app which is built in conjunction with an arduino compatible board. They connect via Bluetooth allowing advanced creative DSLR controls set inside the App to be sent to the small Atemiga chip controlling the camera. For more info read http://rozzles.com/torocam or http://tomgarry.co.uk/torocam and visit the Facebook news page: https://www.facebook.com/toroCam

Availability
============
At the time of writing (Dec 2013) toroCam boards are only available to developers and early testers, if you're interested in joining this programme please get in contact through the facebook page or via email (me@rorycrispin.co.uk) 
Developer editions of the toroCam pro can be obtained in exchange for a £30 donation to the project, plus international postage if you're not from the UK.  

Contributing
============
We've designed toroCam from the ground up to be an open source project that anyone can contribute to so we're really excited to invite new developers to the project. Here's the basic setup for making new functions and communicating with the toroCam board.

Adding functions
----------------
There are a couple of steps to creating a new toroCam function, firstly you need to create a new activity and add a link to it from the FlatHome activity, you can do so by copy/pasting one of the linearlayouts in the FlatHome xml to the bottom of the list, make sure to change the name of it and the onClick action to a new method like this: 

	public void templateClick(View v) {
		try    {
			Intent newIntent = new Intent(v.getContext(), TemplateTrigger.class);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			System.out.println(ex); 
		}
	}
	
You'll also want to copy over the toroCam logo and back button headers and add the relevant typeface and button action code: 
	Typeface code to go in the activities onCreate AFTER your:
	
	setContentView(R.layout.activity_flat_home);
line or you'll get crashes for setting the typeface before loading the xml. 
	
	Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/robotoLI.otf");
		TextView tv = (TextView) findViewById(R.id.headerTitle);
		tv.setTypeface(tf);


Give the back button a transition like so: 

	@Override
		public void onBackPressed(){
			super.onBackPressed();
			overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
		}
And do the same for the ui back button, this should give you an activity that matches the rest. Also make sure to set the font color of your buttons to white instead of the default black, we've already changed the ui elements to the toroCam green color though. Next step is communicating with the Bluetooth device. 

Using Bluetooth
===============
All the Bluetooth code happens in the BlueComms class which functions as a service which the activities bind to. If your activity needs to communicate with the toroCam board you'll need to bind the service like so: 
Declare this at the top of your activity: 

	BlueComms mServer;
	
In your onCreate add these two lines:

	Intent mIntent = new Intent(this, BlueComms.class);
	bindService(mIntent, mConnection, BIND_AUTO_CREATE);

Add this method to your activity: 

	ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName name) {
			mBounded = false;
			mServer = null;
		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			mBounded = true;
			LocalBinder mLocalBinder = (LocalBinder)service;
			mServer = mLocalBinder.getServerInstance();
		}
	};

And make your onStop look like this: 

	@Override
	protected void onStop() {
		super.onStop();
		if(mBounded) {
			unbindService(mConnection);
			mBounded = false;
		}
	};
	
That's it. You can send data to the toroCam with

	mServer.sendData(String);

Sending data to the totoCam board
---------------------------------
In order to communicate as efficiently as possible over the Bluetooth connection we communicate with the toroCam board in numbers separated by commas. The first number is the method declaration and after that you send the variables separated by commas and end the message with an ! 
For example: 
------------
To simply trigger the shutter, use method 1 with no parameters: 
	1!
A more complex function like timelapse is sent like so: 
	2,seconds,mins,hurs,shots!
To deconstruct this: 2 is the method number for timelapse, the second value is the number of seconds between shots, the third is for minutes, fourth for hours and then the fifth is for the number of shots, 0 being defaulted to infinite. 
Note: 
Previously we used 10 values in total and even if we didn't want to send anything we'd stil send a 0, so Shutter release would look like this: 
	1,0,0,0,0,0,0,0,0,0! 
This is no longer needed but you may still see it in some of the code until it's all been updated. Please don't write any new code with this sytnax as it's inefficient and unnecessary. Thanks!

Guidelines
==========
All work you add to this project will be under the same license we're using, 'Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0)'
We're currently in the process of cleaning up the Android app which has become a bit of a mess of button1's and unstructured names like 'shutterBmodeSeek' and such. If you're pushing new code to the project we expect it to have a solid naming structure with no unnamed or default objects. Other than that we're pretty flex right now. 

License
=======
All code here is licenced under the 'Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0) ' License found here http://creativecommons.org/licenses/by-nc-sa/4.0/
