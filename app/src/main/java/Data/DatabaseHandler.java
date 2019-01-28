package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //creating the table
        String CREATE_CONTACT_TABLE = "CREATE TABLE "+Util.TABLE_NAME + "("
                +Util.KEY_ID + " INTEGER PRIMARY KEY,"+Util.KEY_NAME+" TEXT,"+
                Util.KEY_PHONE_NUMBER+" TEXT"+")";

        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //checks if the current version is less than the new and if that's the case
        // delete the current table and recreate a new one

        //deleting the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Util.TABLE_NAME);

        //recreating table
        onCreate(sqLiteDatabase);
    }


    /**
     *CRUD OPERATIONS
     */
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.KEY_NAME, contact.getName());
        value.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //insert row
        db.insert(Util.TABLE_NAME, null, value);
        db.close();
    }

    //Get a single contact
    public Contact getContact(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{
                Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER
        }, Util.KEY_ID+"=?", new String[]{String.valueOf(id)}, null, null,
                null,null);

        if(cursor != null)
            cursor.moveToFirst();
            /**retrieving the columns in the table.
             * getString(0) is the first column, getString(1) is the second and so on*/
            Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));

            db.close();
            cursor.close();
            return contact;
    }

    //Get all contacts
    public List<Contact> getAllContacts(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> contactList = new ArrayList<>();
        //Select all contacts
        String selectAll = "SELECT * FROM "+Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll,null);
        //Loop thru contacts
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                //Getting the first column which is the ID
                contact.setId(Integer.parseInt(cursor.getString(0)));
                //Getting name
                contact.setName(cursor.getString(1));
                //Getting phone number
                contact.setPhoneNumber(cursor.getString(2));
                //Adding to list
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return contactList;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());
        //update row
        return db.update(Util.TABLE_NAME, values, Util.KEY_ID+"=?",
                new String[]{String.valueOf(contact.getId())});
    }

    //Delete single contact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.KEY_ID+"=?", new String[]{String.valueOf(
                contact.getId()
        )});
        db.close();
    }

    //Get contact count
    public int getContactsCount(){
        String countQuery = "SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


}
