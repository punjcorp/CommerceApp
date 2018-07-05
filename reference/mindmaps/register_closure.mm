<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1530522083016" ID="ID_164402745" MODIFIED="1530583186750" TEXT="Register Closure">
<node CREATED="1530527675051" ID="ID_694600024" MODIFIED="1530527689965" POSITION="right" TEXT="Is the Store OPEN?">
<node CREATED="1530521684185" ID="ID_1548621518" MODIFIED="1530527727797" TEXT="Yes, is the register# provided ?">
<node CREATED="1530521703031" ID="ID_1812481021" MODIFIED="1530583495757" TEXT="No, Retrieve all the registers and display Register Closing screen">
<node CREATED="1530521090854" ID="ID_1246520749" MODIFIED="1530593358245" TEXT="Register Selection is made on Register Closing Screen">
<icon BUILTIN="full-2"/>
<node COLOR="#338800" CREATED="1530521306097" ID="ID_696377560" MODIFIED="1530521968104" TEXT="Clear all the expected and closing amounts (If any)"/>
<node COLOR="#990000" CREATED="1530521310206" ID="ID_968528500" MODIFIED="1530583500588" TEXT=" Go Back to 1."/>
</node>
</node>
<node CREATED="1530519506484" ID="ID_1840446112" MODIFIED="1530525872645" TEXT="Yes, is the register A Valid Register?">
<node CREATED="1530519371511" ID="ID_1041657437" MODIFIED="1530583227756" STYLE="fork" TEXT="Yes - A Valid Register, is it in OPEN STATUS?">
<icon BUILTIN="full-1"/>
<node CREATED="1530521397335" ID="ID_753781902" MODIFIED="1530526239973" TEXT="No, Are all the registers in Closed Status?">
<node COLOR="#ff0000" CREATED="1530521438751" ID="ID_584109046" MODIFIED="1530592158065" TEXT="Yes, Display Error about all register in closed status and Show the options for next actions (Store Closure)"/>
<node COLOR="#ff0000" CREATED="1530519519698" ID="ID_276581671" MODIFIED="1530592134391">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      No, Display Error that the SELECTED register is not in OPEN Status, hence cannot be closed.
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
<node CREATED="1530521419952" ID="ID_997026147" MODIFIED="1530521436588" TEXT="Yes, Display the Store Closure Screen">
<node COLOR="#338800" CREATED="1530519583767" ID="ID_1254140408" MODIFIED="1530526320172" TEXT="Retrieve All the register list, but select the provided register"/>
<node COLOR="#338800" CREATED="1530519724431" ID="ID_640062642" MODIFIED="1530526334948" TEXT="Retrieve the Expected amount from Store Daily Totals for the respective registers"/>
<node COLOR="#990000" CREATED="1530519810011" FOLDED="true" ID="ID_1341472084" MODIFIED="1530583506828" TEXT="If, Register Selection is changed on Register Closing Screen, Back to 1.">
<node COLOR="#338800" CREATED="1530520811353" ID="ID_462845363" MODIFIED="1530526790501" TEXT="Clear all the expected and closing amounts (If any)"/>
<node COLOR="#338800" CREATED="1530526738672" ID="ID_964982129" MODIFIED="1530526758728" TEXT=" Go to Register Closure Root"/>
</node>
</node>
</node>
<node COLOR="#ff0000" CREATED="1530519519698" ID="ID_504916278" MODIFIED="1530526041942">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Display Error that it is not a valid register and Ask user to choose from a valid register
    </p>
  </body>
</html></richcontent>
<node COLOR="#990000" CREATED="1530520414336" ID="ID_651456022" MODIFIED="1530593375385">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Back to 2.
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
</node>
</node>
<node COLOR="#ff0000" CREATED="1530527730361" ID="ID_208772075" MODIFIED="1530527765385" TEXT="No, Display Error that the Store is not in OPEN status, show options to go to Store Open Screen"/>
</node>
</node>
</map>
