# Weather Forecast application
Mobile Developer Assignment at NAB.  Candidate: Doan Ngo

Microsoft Word - Android Take-home Assignment_2022.docx

## Expected Outputs
Tick are which I done:

Microsoft Word - Android Take-home Assignment_2022.docx

1. [x] 1. Programming language: Kotlin is required, Java is optional.
2. [x] 2. Design app's architecture (suggest MVVM)
3. [x] 3. Apply LiveData mechanism
4. [x] 4. UI should be looks like in attachment.
5. [x] 5. Write UnitTests
6. [ ] 6. Acceptance Tests
7. [x] 7. Exception handling
8. [ ] 8. Caching handling
9. Secure Android app from:
	- [ ] Decompile APK
	- [ ] Rooted device
	- [ ] Data transmission via network
	- [ ] Encryption for sensitive information
10. Accessibility for Disability Supports:
	- [x] Talkback: Use a screen reader.
	- [ ] Scaling Text: Display size and font size: To change the size of items on your screen, adjust
11. [ ] 11. Entity relationship diagram for the database and solution diagrams for the components, infrastructure design if any
12. Readme file includes:
	- [x] Brief explanation for the software development principles, patterns & practices being applied
    - [x] Brief explanation for the code folder structure and the key Java/Kotlin libraries and frameworks being used
    - [ ] All the required steps in order to get the application run on local computer
    - [ ] Checklist of items the candidate has done.

## Brief Explanation
### Architecture
For this assignment, I uses Clean Architecture, and MVVM architectural pattern for presentation layer.

Since Clean Architecture will separate 3 layers Presentation, Domain, and Data into 3 modules, then developer cannot violate dependency rules between layers (for example Domain layer cannot access class from Presentation, and Data layers, or framework classes).
But in my assignment, I am separating it into 3 packages for simple purpose, but I still kept in mind about the dependency rules. I am very appricicate if you forgive me for this thing.

About the MVVM, there are some things I created may interesting to you:
-  `ViewStateStore.kt`: I created it by get inspired from Flutter state management.
- `BaseViewModel.kt`: I created it to make state management better, and fire event easier.
- `XXXDisplayModel.kt`: I wrapped all classes, enums which will be used to display data at presentation layer.
- `XXXEvent.kt`: I used `sealed` then it help me easier to handle all event with `when` condition statement.


### Folder/Package structure

```
+--- <root package> (com.nab.doanngo.weathersapp)
|	+--- data
|	|	+--- datasources
|	|	|	+--- remote
|	|	|	|	+--- entities
|	|	|	+--- local
|	|	|	|	+--- entities
|	|	+--- di (`di` This package contains classes for Hilt(Dagger 2))
|	|	+--- repositories // Repository implementation from repository interface at domain layer
|	|	+--- utils
|	+--- di
|	+--- domain
|	|	+--- models
|	|	|	+--- dtos
|	|	|	+--- exceptions // Define exception classes
|	|	+--- repositories // Define repository interfaces
|	|	+--- usecases
|	+--- domain
|	|	+--- models
|	|	|	+--- dtos
|	|	|	+--- exceptions
|	|	+--- repositories
|	|	+--- usecases
|	+--- presenation
|	|	+--- base
|	|	+--- utils
|	|	+--- features
|	|	|	+--- search
|	|	|	+--- home
|	+--- WeathersApplication.kt
```

### Others
Somethings you may disagree at my code, then I would like to explain first:

#### Defined date format in strings.xml
`<string name="item_weather_forecast_day_date_format">EEE, dd MMM yyyy</string>`

I think we could define date format at strings.xml, since for each language, country, they would like have their date format which usually use. For example, like Vietnamese, I usually use `dd/MM/yyyy`, but when I worked for Japan project, they usually use `yyyy/MM/dd`.

### Opensources

There is one class (`UseCase.kt`) I copied from Google code, and update a little bit (use `Result` from Kotlin instead)