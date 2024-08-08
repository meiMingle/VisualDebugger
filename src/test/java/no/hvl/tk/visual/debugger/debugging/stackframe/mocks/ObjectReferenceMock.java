package no.hvl.tk.visual.debugger.debugging.stackframe.mocks;

import com.sun.jdi.*;
import java.util.*;
import no.hvl.tk.visual.debugger.debugging.stackframe.mocks.value.BooleanValueMock;
import org.jetbrains.annotations.NotNull;

public class ObjectReferenceMock<E extends Value> implements ObjectReference, Iterable<E> {
  private final long id;
  private final ReferenceTypeMock type;
  private final HashMap<Field, Value> fields;
  private Collection<E> itSource = new HashSet<>();
  private Map<E, E> itMapSource = new HashMap<>();

  private Iterator<E> runningIt = null;
  private Iterator<Map.Entry<E, E>> runningMapIt = null;
  private boolean itShouldBeMap = false;

  public static ObjectReferenceMock<Value> create(final String typeName) {
    return new ObjectReferenceMock<>(typeName);
  }

  public static <A extends Value> ObjectReferenceMock<A> createCollectionObjectRefMock(
      final String typeName, final Collection<A> content) {
    final ObjectReferenceMock<A> aObjectReferenceMock = new ObjectReferenceMock<>(typeName);
    aObjectReferenceMock.referenceType().addInterface(new InterfaceTypeMock(typeName));
    aObjectReferenceMock.setIteratorSource(content);
    return aObjectReferenceMock;
  }

  public static <A extends Value> ObjectReferenceMock<A> createMapObjectRefMock(
      final String typeName, final Map<A, A> content) {
    final ObjectReferenceMock<A> aObjectReferenceMock = new ObjectReferenceMock<>(typeName);
    aObjectReferenceMock.referenceType().addInterface(new InterfaceTypeMock(typeName));
    aObjectReferenceMock.setItMapSource(content);
    return aObjectReferenceMock;
  }

  @NotNull @Override
  public Iterator<E> iterator() {
    return this.itSource.iterator();
  }

  private ObjectReferenceMock(final String typeName) {
    this.type = new ReferenceTypeMock(typeName);
    this.id = StringReferenceMock.idCounter.incrementAndGet();
    this.fields = new HashMap<>();
  }

  @Override
  public ReferenceTypeMock referenceType() {
    return this.type;
  }

  @Override
  public Value getValue(final Field field) {
    return this.fields.get(field);
  }

  @Override
  public Map<Field, Value> getValues(final List<? extends Field> fields) {
    return this.fields;
  }

  @Override
  public void setValue(final Field field, final Value value) {
    this.fields.put(field, value);
  }

  @Override
  public Type type() {
    return new TypeMock(this.type.name());
  }

  private void setIteratorSource(final Collection<E> collection) {
    this.itSource = collection;
  }

  // Below is irrelevant

  @Override
  public Value invokeMethod(
      final ThreadReference thread,
      final Method method,
      final List<? extends Value> arguments,
      final int options) {
    if (method.name().equals("iterator")) {
      if (this.itShouldBeMap) {
        this.runningMapIt = this.itMapSource.entrySet().iterator();
      } else {
        this.runningIt = this.itSource.iterator();
      }
      return this;
    }
    if (method.name().equals("hasNext")) {
      if (this.itShouldBeMap) {
        return new BooleanValueMock(this.runningMapIt.hasNext());
      } else {
        return new BooleanValueMock(this.runningIt.hasNext());
      }
    }
    if (method.name().equals("next")) {
      if (this.itShouldBeMap) {
        final Map.Entry<E, E> next = this.runningMapIt.next();
        return new EntryObjectReferenceMock(next.getKey(), next.getValue());
      } else {
        return this.runningIt.next();
      }
    }
    if (method.name().equals("entrySet")) {
      this.itShouldBeMap = true;
      return this;
    }
    return null;
  }

  public void setItMapSource(final Map<E, E> itMapSource) {
    this.itMapSource = itMapSource;
  }

  @Override
  public void disableCollection() {
    // Irrelevant
  }

  @Override
  public void enableCollection() {
    // Irrelevant
  }

  @Override
  public boolean isCollected() {
    return false;
  }

  @Override
  public long uniqueID() {
    return this.id;
  }

  @Override
  public List<ThreadReference> waitingThreads() {
    return null;
  }

  @Override
  public ThreadReference owningThread() {
    return null;
  }

  @Override
  public int entryCount() {
    return 0;
  }

  @Override
  public List<ObjectReference> referringObjects(final long maxReferrers) {
    return null;
  }

  @Override
  public VirtualMachine virtualMachine() {
    return null;
  }
}
