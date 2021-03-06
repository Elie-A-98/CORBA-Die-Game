package DieGameApp;


/**
* DieGameApp/_PlayerIntStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from DieGameApp.idl
* Friday, July 3, 2020 6:27:30 AM EEST
*/


//Player
public class _PlayerIntStub extends org.omg.CORBA.portable.ObjectImpl implements DieGameApp.PlayerInt
{

  public void callback (String msg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("callback", true);
                $out.write_string (msg);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                callback (msg        );
            } finally {
                _releaseReply ($in);
            }
  } // callback

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:DieGameApp/PlayerInt:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _PlayerIntStub
