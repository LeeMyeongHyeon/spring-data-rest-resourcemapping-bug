# spring-data-rest-resourcemapping-bug

This test case is so wired.

check out :
https://github.com/LeeMyeongHyeon/spring-data-rest-resourcemapping-bug/blob/master/src/test/java/com/example/sample/IntegrationTest.java

There are one parent(Animal) and two child(Dog, Cat) classes.

I want to expose 'animals' under '_embedded' json value ;  not dogs, cats

like this :
``` 
"_embedded" : {
    "animals" : [ {
      "name" : "dog",
      "message" : "hi",
      "_links" : {
        "self" : {
          "href" : "/animals/com.example.sample.Animal$Id@2d07aacc"
        }
      }
    }, {
      "name" : "cat",
      "message" : "hi2",
      "_links" : {
        "self" : {
          "href" : "/animals/com.example.sample.Animal$Id@ff5d4f1"
        }
      }
    } ]
  }
}
```

I used @Relation on entities, implements @RestController manually and id is @EmbeddedId.

First call result is ok but, after calls are different.

second call :
```
"_embedded" : {
    "cats" : [ {
      "name" : "cat",
      "message" : "hi2",
      "_links" : {
        "self" : {
          "href" : "/animals/com.example.sample.Animal$Id@553fbe94"
        }
      }
    } ],
    "dogs" : [ {
      "name" : "dog",
      "message" : "hi",
      "_links" : {
        "self" : {
          "href" : "/animals/com.example.sample.Animal$Id@7cdc4070"
        }
      }
    } ]
  }
}
```

### Reason
1. I implements @RestController manually that sub types aren't registered cache. 
(PersistentEntitiesResourceMappings.hasMappingFor() is false)
2. So RelProvider choose AnnotationRelProvider; second priority.
3. Finally PersistentEntityJackson2Module call PersistentEntitiesResourceMappings.getMetadataFor() then put to cache these sub types.
(Because of @EmbeddedId is Association)
4. Second call's result are different. RepositoryRelProvidre find these types in cache that RepositoryRelProvider choosed.




