package DieGameApp;

/**
* DieGameApp/ServerIntHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from DieGameApp.idl
* Friday, July 3, 2020 6:27:30 AM EEST
*/


//Server
public final class ServerIntHolder implements org.omg.CORBA.portable.Streamable
{
  public DieGameApp.ServerInt value = null;

  public ServerIntHolder ()
  {
  }

  public ServerIntHolder (DieGameApp.ServerInt initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DieGameApp.ServerIntHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DieGameApp.ServerIntHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DieGameApp.ServerIntHelper.type ();
  }

}