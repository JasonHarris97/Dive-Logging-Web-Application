L.mapbox.accessToken = 'pk.eyJ1IjoiamFzb24zeXAiLCJhIjoiY2s3ZW4waXl2MHo0MzNucGF4NHQ5bXZ4diJ9.P93UqztpQcrq70aYz1XETQ';

var longitude = [[${dive.longitude}]];
var latitude = [[${dive.latitude}]];
var description = [[${dive.description}]];

var map = L.mapbox.map('map')
    .setView([latitude, longitude], 9)
    .addLayer(L.mapbox.styleLayer('mapbox://styles/mapbox/streets-v11'));

var marker = L.marker([latitude, longitude], {
	icon: L.mapbox.marker.icon({
		'marker-size': 'large',
	        'marker-symbol': 'circle',
	        'marker-color': '#4169E1'
	    	})
	})

marker.bindPopup(description);
marker.addTo(map);
