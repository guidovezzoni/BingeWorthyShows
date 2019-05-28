# BingeWorthyShows

The app shows the most popular TV shows from The Movie Database.

From the architectural point of view I've used a repository pattern for the lower level (which can be easily extended to support multiple levels of cache) and a MVVM pattern with Rx on the higher level (using the Androidx ViewModel).
The code is written mainly in Java a part from some data classes and utility classes - I'm familiarising with kotlin and introducing it in my projects.
I added some sample of unit tests, coverage is low but the purpose is to provide some sample of the way I unit test my classes.
As for 3rd party, the network layer is implemented with Retrofit and Rx as it seems a very convenient way to do it.
Glide is used for image loading as it's a simple and powerful tool.

The branch `master-repofactory-demo` (<https://github.com/guidovezzoni/BingeWorthyShows/tree/master-repofactory-demo>) contains a version of the app that uses my own library <https://github.com/guidovezzoni/repofactory>.
RepoFactory allows to create a simple repository pattern using standard interfaces: those interfaces and some base implementations replace those currently in use in the `master` branch 

# Highlights

## Project Level
* custom .gitignore
* minor improvements on gradle files to better support additional modules
* packages organised on a per-feature basis + common folder

## Code Level
* Project uses the new AndroidX
* lint issues kept to a minimum
* code was commented where not self-explanatory, always trying to stick with javadoc syntax
* some simple kotlin data and utilities classes
* Unit test samples with mockito, and rx testing

## Architectural level
* MVVM and Rx with arch component's ViewModel
* basic repository pattern extensible to support cache - based on Rx and Retrofit
* DI manager - could be replaced by Dagger in more complex projects
* Where possible an interface or a base class with generics has been introduced - viewmodel, repository, etc - to make the code more general.
Usually these classes are in the `baselibrary` folder
The idea is to extract these into an external library which I have partially done in this branch <https://github.com/guidovezzoni/BingeWorthyShows/tree/master-repofactory-demo> using my own library <https://github.com/guidovezzoni/repofactory> 
Several issues in the repository's architecture have been fixed, however those details are visible inside the library code rather than in the app code.

## 3rd party tools/code:
* model pojos generated using <http://www.jsonschema2pojo.org/>
* network calls via retrofit and Rx
* Glide for image loading
* Arrow for optional, conditions and functional programming bits
* bits and pieces coming from SO and Medium posts

## Known issues
* detail page is missing
* pagination currently allows to go over the 1000 pages available in the API
