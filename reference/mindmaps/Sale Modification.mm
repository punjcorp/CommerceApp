<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1536811414584" ID="ID_146457592" MODIFIED="1536811442903" TEXT="Sale Modification">
<node CREATED="1536811449376" ID="ID_1252957333" MODIFIED="1536812614074" POSITION="right">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <table border="0" style="width: 80%; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-width: 0; border-right-width: 0; border-bottom-width: 0; border-left-width: 0">
      <tr>
        <td valign="top" style="width: 100%; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-width: 1; border-right-width: 1; border-bottom-width: 1; border-left-width: 1">
          <p style="margin-top: 1; margin-right: 1; margin-bottom: 1; margin-left: 1">
            Last Bill Last Business Date
          </p>
          <ul>
            <li>
              The business date can be changed
            </li>
            <li>
              No change in seq
            </li>
          </ul>
        </td>
      </tr>
    </table>
  </body>
</html>
</richcontent>
<node CREATED="1536813210281" ID="ID_1775864257" MODIFIED="1536834848796">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Pre Conditions:
    </p>
    <ol>
      <li>
        The new business date store and register should be open.
      </li>
      <li>
        There should be no transaction in new business date so far.
      </li>
    </ol>
    <p>
      Actions:
    </p>
    <ol>
      <li>
        Make adjustments in the daily totals from last business date
      </li>
      <li>
        update receipts, EOD reports
      </li>
      <li>
        Push the transaction to new business date, make changes in all txn details, receipts
      </li>
      <li>
        make changes in daily totals for new business date
      </li>
      <li>
        Track the changes in another set of tables.
      </li>
    </ol>
  </body>
</html>
</richcontent>
</node>
</node>
<node CREATED="1536811505588" ID="ID_596234659" MODIFIED="1536811511783" POSITION="left" TEXT="Previous Business Date">
<node CREATED="1536811519084" ID="ID_425268808" MODIFIED="1536812556141">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      
    </p>
    <table border="0" style="width: 80%; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-width: 0; border-right-width: 0; border-bottom-width: 0; border-left-width: 0">
      <tr>
        <td valign="top" style="width: 100%; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-width: 1; border-right-width: 1; border-bottom-width: 1; border-left-width: 1">
          <p style="font-weight: normal; font-style: normal">
            Push Previous Invoices by One from a past Business Date
          </p>
          <ul>
            <li>
              No Changes in Business Date
            </li>
            <li>
              Only Change in Invoice No
            </li>
            <li>
              A duplicate bill created which will be edited for new invoice details
            </li>
          </ul>
        </td>
      </tr>
    </table>
    <p>
      <br />
    </p>
  </body>
</html>
</richcontent>
<node CREATED="1536833858412" ID="ID_721936876" MODIFIED="1536834837219">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Pre Conditions:
    </p>
    <ol>
      <li>
        There should be no e-way bill
      </li>
      <li>
        There should be no bill which has been sent to customer already.
      </li>
    </ol>
    <p>
      Actions:
    </p>
    <ol>
      <li>
        Duplicate the provided Transaction details , receipts
      </li>
      <li>
        Increase the invoice nos by 1 and recreate all txn receipts
      </li>
      <li>
        Update the txn seq for duplicate txn and update the invoice no
      </li>
      <li>
        .Update the daily total for the modified business Date
      </li>
      <li>
        Update the SKU inventory
      </li>
      <li>
        Track the changes in another set of tables.
      </li>
    </ol>
    <p>
      
    </p>
    <p>
      
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
<node CREATED="1536811533214" ID="ID_1714245600" MODIFIED="1536812580185">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <table border="0" style="width: 80%; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-width: 0; border-right-width: 0; border-bottom-width: 0; border-left-width: 0">
      <tr>
        <td valign="top" style="width: 100%; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-width: 1; border-right-width: 1; border-bottom-width: 1; border-left-width: 1">
          <p style="font-weight: normal; font-style: normal">
            Edit details of existing invoice in a business date
          </p>
          <ul>
            <li>
              No Changes in Business Date
            </li>
            <li>
              No Changes in Invoice No
            </li>
          </ul>
        </td>
      </tr>
    </table>
  </body>
</html>
</richcontent>
<node CREATED="1536834464651" ID="ID_930283354" MODIFIED="1536834830603">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Pre Conditions:
    </p>
    <ol>
      <li>
        There should be no e-way bill for the same invoice
      </li>
      <li>
        The invoice should not have been sent to customer already.
      </li>
    </ol>
    <p>
      Actions:
    </p>
    <ol>
      <li>
        Modify all the transaction details and create modified receipt
      </li>
      <li>
        update the daily totals based on the changes.
      </li>
      <li>
        Update the inventory updates based on the changes.
      </li>
      <li>
        Track the changes in another set of tables.
      </li>
    </ol>
    <p>
      
    </p>
    <p>
      
    </p>
    <p>
      
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
</node>
<node CREATED="1536811957420" ID="ID_702810474" MODIFIED="1536813202775" POSITION="right">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <table border="0" style="width: 80%; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-width: 0; border-right-width: 0; border-bottom-width: 0; border-left-width: 0">
      <tr>
        <td valign="top" style="width: 100%; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-width: 1; border-right-width: 1; border-bottom-width: 1; border-left-width: 1">
          <p style="margin-top: 1; margin-right: 1; margin-bottom: 1; margin-left: 1">
            Delete unused bill from a previous business date by one
          </p>
          <ul>
            <li>
              Change all the sequences by reducing since business date
            </li>
          </ul>
          <p style="margin-top: 1; margin-right: 1; margin-bottom: 1; margin-left: 1">
            
          </p>
        </td>
      </tr>
    </table>
  </body>
</html>
</richcontent>
<node CREATED="1536833127678" ID="ID_126328525" MODIFIED="1536834854256">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Pre Conditions:
    </p>
    <ol>
      <li>
        There should be no e-way bill
      </li>
      <li>
        There should be no bill which has been sent to customer already.
      </li>
    </ol>
    <p>
      Actions:
    </p>
    <ol>
      <li>
        Delete Transaction details , receipts
      </li>
      <li>
        Update Daily Totals
      </li>
      <li>
        Post inventory
      </li>
      <li>
        update all the invoice nos for post transactions and generate new receipts.
      </li>
      <li>
        Track the changes in another set of tables.
      </li>
    </ol>
  </body>
</html>
</richcontent>
</node>
</node>
</node>
</map>
