## entity-sync-utils - Java object graph diff/patch/compare/clone utilities

Simple Java library that can be used to diff two Java object graphs (including primitive/object Arrays, Lists, Sets, Maps...). It will try to compare collections as ordered but if it detects that order is not preserved it will fall back to unordered comparison mode. After the diff is created, the same diff can be applied as patch on existing object. Since diff-patch is same as clone on empty object, this library also supports deep object clone. All object are treated by default as Value Objects (each field in the object is treated as a key). This can be overridden using EntityDefinition - this way you can set one or multiple properties to be treated as Entity key (e.g. "id"). Full recursive structures are supported (circular references).

## List of features

* Compare arbitrary Java object graph, generate Diff and apply it as a Patch on existing object
* Clone object graph (create new object from exsiting one with same content)
* Support for recursive structures (detects circular references)
* Maintains order in Arrays and Lists and supports arbitrary order in Sets (and other unordered collections)
* No need for modification of existing POJOs

## How to use it?

Suppose you have following object hierarchy: BaseEntity <- ChildEntity <- GrandChildEntity with following properties (per class in hierarchy)

* BaseEntity properties ("id", "someBaseString")
* ChildEntity properties ("someChildString")
* GrandChildEntity properties ("id", "someGrandChildString")

Suppose you want to use two "id" properties (from BaseEntity and from GrandChildEntity) as key properties. If you want to make diff, apply as patch and compare if the results after patching is correct (using compare) it would look like this piece of code:

```java
GrandChildEntity gce1 = new GrandChildEntity(1);
GrandChildEntity gce2 = new GrandChildEntity(2);

EntityDefinition entityDefinition = new EntityDefinition(GrandChildEntity.class, "id").registerSuperclass(BaseEntity.class, "id");
Diff diff = new Diff.Builder().registerEntity(entityDefinition).build();
Patch patch = new Patch.Builder().build();
Compare compare = new Compare.Builder().build();

Element<Name, GrandChildEntity> diffElement = diff.diff(gce1, gce2);
GrandChildEntity patchedEntity = patch.patch(new GrandChildEntity(1), diffElement);
boolean areEqual = compare.compare(patchedEntity, new GrandChildEntity(2));
```

In case that you need a quick object clone:

```java
GrandChildEntity gce1 = new GrandChildEntity(1);

Clone cloner = new Clone.Builder().build();

GrandChildEntity cloned = cloner.clone(gce1);
```

The full usage samples are in Test folder of this project.
