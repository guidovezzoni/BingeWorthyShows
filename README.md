# BingeWorthyShows

From the architectural point of view I've used a repository pattern for the lower level (which can be easily extended to support multiple levels of cache) and a MVVM pattern with Rx on the higher level (using the Androidx ViewModel). The code is written mainly in Java a part from some data classes and utility classes - I'm starting to familiarise with kotlin and introducing it in my projects. I added some sample of unit tests, coverage is low but the purpose is to provide some sample of the way I unit test my classes. As for 3rd party, the network layer is implemented with Retrofit and Rx as it seems a very convenient way to do it. Glide is used for image loading as it's a simple and powerful tool.

# Highlights

## Project Level
* custom .gitignore
* minor improvements on gradle files to better support additional module
* packages organised on a per-feature basis + common folder

## Code Level
* Unit test samples with mockito, and rx testing
* Project uses the new AndroidX
* lint issues kept to a minimum
* code was commented where not self-explanatory, always trying to stick with javadoc syntax
* some simple kotlin data and utilities classes

## Architectural level
* basic repository pattern extensible to support cache - Rx and Retrofit
* MVVM and Rx with arch component's ViewModel
* DI manager - could be replaced by Dagger in more complex projects

## 3rd party tools/code:
* model pojos generated using http://www.jsonschema2pojo.org/
* network calls via retrofit and Rx
* Glide for image loading
* Arrow for optional, conditions and functional programming bits
* bits and pieces coming from SO and Medium posts

## Known issues
* detail page is still missing
* pagination currently allows to go over the 1000 pages available in the API
