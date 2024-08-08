package no.hvl.tk.visual.debugger.debugging.stackframe.mocks;

import com.sun.jdi.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ArrayReferenceMock implements ArrayReference {
  private static final String ARRAY = "Array";
  private final long id;
  private final Value[] content;

  public ArrayReferenceMock(final List<Value> content) {
    this.content = content.toArray(new Value[0]);
    this.id = StringReferenceMock.idCounter.incrementAndGet();
  }

  @Override
  public long uniqueID() {
    return this.id;
  }

  @Override
  public int length() {
    return this.content.length;
  }

  @Override
  public Value getValue(final int i) {
    return this.content[i];
  }

  @Override
  public ReferenceType referenceType() {
    return new ReferenceTypeMock(ARRAY);
  }

  @Override
  public Type type() {
    return new TypeMock(ARRAY);
  }

  @Override
  public List<Value> getValues() {
    return Arrays.asList(this.content);
  }

  // Below is irrelevant.

  @Override
  public List<Value> getValues(final int i, final int i1) {
    return null;
  }

  @Override
  public void setValue(final int i, final Value value) {
    // Irrelevant
  }

  @Override
  public void setValues(final List<? extends Value> list) {
    // Irrelevant
  }

  @Override
  public void setValues(final int i, final List<? extends Value> list, final int i1, final int i2) {
    // Irrelevant
  }

  @Override
  public Value getValue(final Field field) {
    return null;
  }

  @Override
  public Map<Field, Value> getValues(final List<? extends Field> list) {
    return null;
  }

  @Override
  public void setValue(final Field field, final Value value) {
    // Irrelevant
  }

  @Override
  public Value invokeMethod(
      final ThreadReference threadReference,
      final Method method,
      final List<? extends Value> list,
      final int i) {
    return null;
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
  public List<ObjectReference> referringObjects(final long l) {
    return null;
  }

  @Override
  public VirtualMachine virtualMachine() {
    return null;
  }
}
