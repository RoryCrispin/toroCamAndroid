toroCam Camera Control
======================

toroCam is a camera control system, which gives you unparalleled control over your SLR though an open source app, and a small, Arduino compatible board.

This repo hosts the Android app is built in conjunction with an arduino compatible board. They connect via bluetooth allowing advanced creative DSLR controls set inside the App to be sent to the small Atemiga chip controlling the camera. For more info email me:

Avalibility
===========
At the time of writing (Dec 2013) toroCam boards are only avalible to developers and early testers, if you're interested in joining this programme please get in contact with me@rorycrispin.co.uk, developer editions of the toroCam pro can be obtained in exchange for a £30 donation to the project, plus international postage if you're not from the UK. 

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
	Typeface code to go in the activities onCreate AFTER your 'setContentView(R.layout.activity_flat_home);' line! 
	
	Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/robotoLI.otf");
		TextView tv = (TextView) findViewById(R.id.s1Text);
		tv.setTypeface(tf);
		

Guidelines
==========
All work you add to this peoject will be under the same license we're using, 'CreativeCommons Attribution-ShareAlike 3.0 Unported'
We're currently in the process of cleaning up the Android app which has become a bit of a mess of button1's and unstructured names like 'shutterBmodeSeek' and such. If you're pushig new code to the project we expect it to have a solid naming structure with no unnamed or default objects. Other than that we're pretty flex right now. 

License
=======
All code here is licenced under the 'CreativeCommons Attribution-ShareAlike 3.0 Unported' License found here http://creativecommons.org/licenses/by-sa/3.0/
