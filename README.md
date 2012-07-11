# Ti2DScrollView Module

## Description

This implementation enables Android to scroll a view in vertically and horizontally, (2D).  


## Accessing the Ti2DScrollView Module

To access this module from JavaScript, you would do the following:

	var androidscroll = require("dk.napp.androidscroll");

The androidscroll variable is a reference to the Module object.	

	var Ti2DScrollView = require("dk.napp.androidscroll");
	var scrollView = new Ti2DScrollView.createNappscroll({
		contentWidth:'auto',
		contentHeight:'auto',
		maxZoomValue:4,
		minZoomValue:0.4,
		top:0,
		bottom:0,
		showVerticalScrollIndicator:true,
		showHorizontalScrollIndicator:true
	});
	
	scrollView.addEventListener('pinchStart',function(e) {
		Ti.API.debug("pinchStart");
	});
	
	scrollView.addEventListener('pinch',function(e) {
		Ti.API.debug("pinch: " + e.scale );
		
		// THIS IS NOT A GREAT SOLUTION - BAD FOR PERFORMANCE
		// WORKING SCALE - But scrollView contentWidth/contentHeight is not updating accordenly 
		var t = Ti.UI.create2DMatrix().scale(e.scale);
		currentScale = e.scale;
		containerView.transform = t;
	});
	
	scrollView.addEventListener('pinchEnd',function(e) {
		Ti.API.debug("pinchEnd");
	});
	
	
	See `example/app.js` for the entire example code

## Methods

scrollTo(x,y)

## TODO:

- Native Pinch zoom: At this point the zooming is handled by javascript. We need this to be done native.
- Scroll Bars: There are no scrollbars. 

## Author

**Mads Møller**

web: http://www.napp.dk

email: mm@napp.dk

## License

	Copyright (c) 2010-2011 Mads Møller

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
