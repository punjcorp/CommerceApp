<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1530519039736" ID="ID_1072871197" MODIFIED="1530583357032" TEXT="Store Closure">
<node CREATED="1530521684185" ID="ID_1548621518" MODIFIED="1530522118063" POSITION="right" TEXT="is the store# provided ?">
<node CREATED="1530521703031" ID="ID_1812481021" MODIFIED="1530521746420" TEXT="No, Retrieve all the valid locations and display Store Closing screen">
<node CREATED="1530521090854" ID="ID_1246520749" MODIFIED="1530521988204" TEXT="Location Selected for Closure">
<node COLOR="#338800" CREATED="1530521306097" ID="ID_696377560" MODIFIED="1530521968104" TEXT="Clear all the expected and closing amounts (If any)"/>
<node COLOR="#990000" CREATED="1530521310206" ID="ID_968528500" MODIFIED="1530583484499" TEXT=" Go to 1."/>
</node>
</node>
<node CREATED="1530519506484" ID="ID_1840446112" MODIFIED="1530521777884" TEXT="Yes, is the store A Valid Store?">
<node CREATED="1530519371511" ID="ID_1041657437" MODIFIED="1530521886042" STYLE="fork" TEXT="Yes - A Valid Store, is it in OPEN STATUS?">
<node COLOR="#ff0000" CREATED="1530519519698" ID="ID_276581671" MODIFIED="1530521228727">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      NO, Display Error that the store is not in OPEN Status, hence cannot be closed.
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1530521397335" ID="ID_753781902" MODIFIED="1530583387144" TEXT="Yes, Are all the registers in Closed Status?">
<icon BUILTIN="full-1"/>
<node CREATED="1530521419952" ID="ID_997026147" MODIFIED="1530521436588" TEXT="Yes, Display the Store Closure Screen">
<node COLOR="#338800" CREATED="1530519583767" ID="ID_1254140408" MODIFIED="1530520892366" TEXT="Retrieve All the Location list, but select the provided store"/>
<node COLOR="#338800" CREATED="1530519724431" ID="ID_640062642" MODIFIED="1530520892366" TEXT="Retrieve the Expected amount from Store Daily Totals"/>
<node COLOR="#338800" CREATED="1530519762157" ID="ID_875917496" MODIFIED="1530520892366">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Retrieve actual amount based on
    </p>
    <p>
      Register Close Records with denominations
    </p>
  </body>
</html></richcontent>
</node>
<node COLOR="#990000" CREATED="1530519810011" ID="ID_1341472084" MODIFIED="1530583456641" TEXT="If, the location changed on Store Closing Screen, Go to 1.">
<font NAME="Calibri Light" SIZE="12"/>
</node>
</node>
<node COLOR="#ff0000" CREATED="1530521438751" ID="ID_584109046" MODIFIED="1530583455164" TEXT="No, Display Error about not all register in closed status and Show the options for next actions"/>
</node>
</node>
<node COLOR="#ff0000" CREATED="1530519519698" ID="ID_504916278" MODIFIED="1530521028115">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Display Error that it is not a valid store and Ask user to choose from a valid location
    </p>
  </body>
</html></richcontent>
<node COLOR="#338800" CREATED="1530520414336" ID="ID_651456022" MODIFIED="1530521088065">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Display the Screen with all the valid locations and Retrieve the closing amounts as needed
    </p>
  </body>
</html></richcontent>
<node CREATED="1530521090854" ID="ID_618075159" MODIFIED="1530521304780" TEXT="Location is changed on Screen">
<node COLOR="#338800" CREATED="1530521306097" ID="ID_138181836" MODIFIED="1530522021160" TEXT="Clear all the expected and closing amounts"/>
<node COLOR="#990000" CREATED="1530521310206" ID="ID_1406059035" MODIFIED="1530583481827" TEXT="Go to 1."/>
</node>
<node COLOR="#990000" CREATED="1530521130704" ID="ID_475080306" MODIFIED="1530522003480" TEXT="Stop"/>
</node>
</node>
</node>
</node>
</node>
</map>
