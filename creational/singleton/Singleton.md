## Singleton

### When and Why?

#### Consistency across instances:

Singleton is useful when we have **dynamic** data, which has to be the same across all the places where it is accessed.
Since the data is dynamic, it will change, if a new instance of the class is created in every place where it is needed,
and we change one of the objects, due to how memory references work, it will not be propagated across all the other
instances.
Thus we will have inconsistent state across the places where the given data is accessed.
Singleton resolves this issue by always returning the same instance, if it's data is changed somewhere, all the other
places will also have the same changes
since they have the same instance of the object.

If the data is **static**, like database urls, or passwords, or anything which will never change during the lifetime of
the application,
we should make the data immutable. Once the object is created, it should not be possible to change the data anyhow.
Therefor even if we have 30 objects, it will be impossible to have different state, although now we have a resource
issue.

#### Excessive resource usage by re-instantiating an object:

If we look at the previous example for the immutable static data, _which does not prevent the creation of new objects_,
and in case that the creation requires an expensive operation, like reading from a file, which involves I/O operations,
it would cost quite a lot of resources if we crate a new object everytime we need it.
Singleton resolves this issue by instantiating an object once, and then it will return the same object everytime a new
one needs to be created.
We can be sure that there is no way the computational operation is performed more then necessary.Ï