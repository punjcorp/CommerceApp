<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1535083153251" ID="ID_238016059" MODIFIED="1535087244018" TEXT="Order Return">
<node CREATED="1535083168936" ID="ID_1257606295" MODIFIED="1535083179585" POSITION="right" TEXT="Create Order Return">
<node CREATED="1535083181038" ID="ID_752725548" MODIFIED="1535083192596" TEXT="is Order Number provided?"/>
<node CREATED="1535083193197" ID="ID_724273588" MODIFIED="1535083199746" TEXT="is Order in valid status?"/>
<node CREATED="1535083200623" ID="ID_1356492847" MODIFIED="1535083213913" TEXT="Does order has items to be returned?"/>
<node CREATED="1535083214629" ID="ID_1502960839" MODIFIED="1535083239625" TEXT="if Yes for all of the above">
<node CREATED="1535083241855" ID="ID_859197867" MODIFIED="1535084891914" TEXT="save return items with reason codes and comments"/>
<node CREATED="1535084867398" ID="ID_1875637925" MODIFIED="1535084884002" TEXT="approve return items based on return qty">
<node CREATED="1535084902278" ID="ID_1793705594" MODIFIED="1535094483157">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      update original order items with qty and
    </p>
    <p>
      update order status if all items has been returned
    </p>
  </body>
</html>
</richcontent>
</node>
<node CREATED="1535084928862" ID="ID_441399029" MODIFIED="1535094505223">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      update the money expected as refund
    </p>
    <p>
      &#160;in the account for supplier
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
<node CREATED="1535085831510" ID="ID_1847351433" MODIFIED="1535085844665" TEXT="generate the report and save in database"/>
</node>
</node>
<node CREATED="1535084973174" ID="ID_1680682905" MODIFIED="1535084984193" POSITION="right" TEXT="Edit Order Return">
<node CREATED="1535084985846" ID="ID_1216727114" MODIFIED="1535085003625" TEXT="Is Return Number provided?"/>
<node CREATED="1535085008311" ID="ID_530951307" MODIFIED="1535085019273" TEXT="Is Return existing in database?"/>
<node CREATED="1535085020358" ID="ID_1571097787" MODIFIED="1535085029873" TEXT="Is Return in valid status for editing?"/>
<node CREATED="1535085030485" ID="ID_1746901186" MODIFIED="1535085045113" TEXT="If Yes for all of the above">
<node CREATED="1535085046198" ID="ID_409758872" MODIFIED="1535085062713" TEXT="Proceed with save "/>
<node CREATED="1535085064293" ID="ID_209450174" MODIFIED="1535085073811" TEXT="Proceed with approval"/>
<node CREATED="1535085849382" ID="ID_399861703" MODIFIED="1535085856657" TEXT="Generate the report and save in database"/>
</node>
</node>
<node CREATED="1535085082822" ID="ID_1791194940" MODIFIED="1535087204736" POSITION="left" TEXT="Validations for Add/Edit Order Return">
<node CREATED="1535085734799" ID="ID_359259371" MODIFIED="1535085745993" TEXT="Is order/return number provided?"/>
<node CREATED="1535085746462" ID="ID_304784121" MODIFIED="1535085757810" TEXT="is order or return in correct status?"/>
<node CREATED="1535085758758" ID="ID_1410821118" MODIFIED="1535085768594" TEXT="is order/return existing in database?"/>
<node CREATED="1535085769463" ID="ID_1982625329" MODIFIED="1535085783417" TEXT="does order has the valid items available for return?"/>
<node CREATED="1535085784086" ID="ID_1838587428" MODIFIED="1535085807418" TEXT="is the return qty is less than equal to available qty"/>
<node CREATED="1535085808470" ID="ID_762086495" MODIFIED="1535085819130" TEXT="is the reason code at global and item level selected?"/>
</node>
<node CREATED="1535087283070" ID="ID_1046554139" MODIFIED="1535087294284" POSITION="left" TEXT="Manage Order Return">
<node CREATED="1535087295870" ID="ID_1974425612" MODIFIED="1535087307512" TEXT="List the Order Returns based on criteria">
<node CREATED="1535087308949" ID="ID_77650803" MODIFIED="1535087315848" TEXT="Order Return Id"/>
<node CREATED="1535087316214" ID="ID_1572104896" MODIFIED="1535087323685" TEXT="Order Id"/>
<node CREATED="1535087366062" ID="ID_904116822" MODIFIED="1535087368496" TEXT="comments"/>
<node CREATED="1535087369093" ID="ID_459614665" MODIFIED="1535087371374" TEXT="created by"/>
<node CREATED="1535087371733" ID="ID_1861158315" MODIFIED="1535087380582" TEXT="supplier id"/>
<node CREATED="1535087381149" ID="ID_1558521545" MODIFIED="1535087384553" TEXT="supplier name"/>
<node CREATED="1535087384958" ID="ID_1105399636" MODIFIED="1535087387421" TEXT="location id"/>
<node CREATED="1535087387757" ID="ID_1144335224" MODIFIED="1535087395181" TEXT="location name"/>
<node CREATED="1535087395669" ID="ID_10285712" MODIFIED="1535087397564" TEXT="item id"/>
<node CREATED="1535087397934" ID="ID_1134678493" MODIFIED="1535087402261" TEXT="item description"/>
</node>
<node CREATED="1535087419222" ID="ID_1504944559" MODIFIED="1535087435954" TEXT="Display Order Return Operations At Record level">
<node CREATED="1535087482534" ID="ID_1105444909" MODIFIED="1535087498141" TEXT="Edit Return"/>
<node CREATED="1535087498557" ID="ID_593921742" MODIFIED="1535087503277" TEXT="Approve Return"/>
<node CREATED="1535087798902" ID="ID_1577096577" MODIFIED="1535087805209" TEXT="Delete Return"/>
<node CREATED="1535087503894" ID="ID_1024901805" MODIFIED="1535087509262" TEXT="Print Report"/>
</node>
<node CREATED="1535087436438" ID="ID_1944979158" MODIFIED="1535087445934" TEXT="Display Bulk Actions">
<node CREATED="1535087450790" ID="ID_1424077307" MODIFIED="1535087458632" TEXT="Approve Order Returns"/>
<node CREATED="1535087459014" ID="ID_1614407463" MODIFIED="1535087463484" TEXT="Delete Order Returns"/>
<node CREATED="1535087464542" ID="ID_124199772" MODIFIED="1535087478470" TEXT="Manage Purchase Orders"/>
</node>
</node>
<node CREATED="1535089223055" ID="ID_1790709778" MODIFIED="1535089234626" POSITION="right" TEXT="Delete Order Return">
<node CREATED="1535084985846" ID="ID_633349678" MODIFIED="1535085003625" TEXT="Is Return Number provided?"/>
<node CREATED="1535085008311" ID="ID_436241665" MODIFIED="1535085019273" TEXT="Is Return existing in database?"/>
<node CREATED="1535085020358" ID="ID_255525599" MODIFIED="1535089279346" TEXT="Is Return in valid status for deletion?"/>
<node CREATED="1535089280462" ID="ID_988461199" MODIFIED="1535094301619" TEXT="If yes for all of the above then proceed for deletion"/>
</node>
</node>
</map>
