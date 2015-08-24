# Introduction #
The core GWT widgets need to be as efficient as possible. Yet, there are many use cases where you would love to have one extra set of divs etc. right here.
The `Decorator` class is the solution to this problem.

# Details #
Each `Decorator` must represent a single static html decorator. Each decorator must be initialized with to DOM elements, in specific the template, and the element within the template to hang the contents off of. Here is an example decorator
```
      Element root = DOM.createDiv();
      root.setClassName("demo-decorator");
      // In this case the root and the element to attach are the same.
      Element toAttach = root;
      Decorator myDecorator = new Decorator(root, toAttach);
```

There are two ways to use decorators, you can either have them wrap a node or a string.
There is a special default decorator that is a straight pass-through, so that the default widget does not pay a runtime cost for having subclasses with decorators.


Each widget which is decorator aware should provide templated methods, for example, `CustomListBox` has the following templated decorators

```
  protected Decorator getButtonDecorator() {
    return Decorator.DEFAULT;
  }

  protected Decorator getItemDecorator() {
    return Decorator.DEFAULT;
  }
```