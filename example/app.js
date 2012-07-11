Titanium.UI.setBackgroundColor('#000');

var win = Ti.UI.createWindow();

var NappScrollView = require("dk.napp.androidscroll");
var scrollView = new NappScrollView.createNappscroll({
	contentWidth:'auto',
	contentHeight:'auto',
	maxZoomValue:4,
	minZoomValue:0.4,
	top:0,
	bottom:0,
	showVerticalScrollIndicator:true,
	showHorizontalScrollIndicator:true
});

var containerView = Ti.UI.createView({
	width:"auto",
	height:"auto",
	touchEnabled:false //important
});

var floorplanView = Ti.UI.createImageView({
	image: "map.png",
	top:0,
	left:0,
	touchEnabled:false, //important
	width:"auto",
	height:"auto"
});

var mappin = Ti.UI.createView({
 	width:50,
 	height:50,
 	top:240,
 	left:350,
 	backgroundColor:"red"
});
 
mappin.addEventListener("click", function(e){
 	alert("click on map pin");
});

scrollView.addEventListener('pinchStart',function(e) {
	Ti.API.error("pinchStart");
});

scrollView.addEventListener('pinch',function(e) {
	Ti.API.error("pinch: " + e.scale );
	
	//WORKING SCALE - But scrollView contentWidth/contentHeight is not updating accordenly 
	var t = Ti.UI.create2DMatrix().scale(e.scale);
	currentScale = e.scale;
	containerView.transform = t;
});

scrollView.addEventListener('pinchEnd',function(e) {
	Ti.API.error("pinchEnd");
});


// methods..
//scrollView.scrollTo(400,500);


//add 
containerView.add(floorplanView);
containerView.add(mappin);

scrollView.add(containerView);
win.add(scrollView);

win.open();

