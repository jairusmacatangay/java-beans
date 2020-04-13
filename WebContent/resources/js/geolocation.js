const mymap = L.map('idMap').setView([14.5636, 120.9951], 17);
const attribution = '&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors';

const tileUrl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
const tiles = L.tileLayer(tileUrl, { attribution });
tiles.addTo(mymap);

const marker = L.marker([14.5636, 120.9951]).addTo(mymap);

if ("geolocation" in navigator) {
	console.log("Geolocation available");
} else {
	console.log("Geolocation not available");
}