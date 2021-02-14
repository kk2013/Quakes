# Quakes

The Quakes app is a simple app that calls the [Earthquakes API](http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-
9.9&east=-22.4&west=55.2&username=mkoppelman). It displays a list of earthquakes and distinguishes those with a magnitude greater than 8. Clicking on an item in the list takes the user to a map showing the location.

It requires the use of a Google Maps API key. To build and run the app, rename app/secure.properties.template to secure.properties and update the value of MAPS_API_KEY with your API key.

### Potential improvements:
- Cache the earthquake data and only refresh at specified intervals
- Enable the user to choose the parameters used to retrieve the earthquake data
- UI enhancements. Only a very simple display is currently used.
- Additional UI tests