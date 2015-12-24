# entity-sync-utils

Simple library to find diff between two Java object graphs. It takes two objects as input and generates diff object graph that can be later used for patching the same object. It supports ordered collections and preserves order. All objects are by deafault treated as Value Objects, but it can be also treated as Entity - you just have to define what to treat as the key. It supporst repeting elements and recursive structures.

Needs still some testing and upgrades...
