# Rapport – innlevering 4
**Team:** *Team Mayhem* – *Adrian, Jon, Malik, Sverre, Andreas, Aleksander*

### Se [Konsept](../konsept.md) og [MVP](../mvp.md)


## Innledning
* Nå som prosjektet er ferdig, kan vi reflektere over hvordan prosessen med å lage et spill i en gruppe på 6 personer uten erfaring med å jobbe i gruppe før har gått. Følgende rapport er en kulminasjon av alle tidligere rapporter og alle erfaringer og andre lærdommer vi har gjort så langt i prosessen.

## Team/Prosjekt
*	Rollene på teamet startet som en statisk fordeling, der en person var ansvarlig for git-bruk, assets og lyd, tre hadde ansvar for spillogikk og to hadde ansvar for meny og HUD. Førstnevnte skulle ha ansvar for alt ting git, som merge requests og å lage guide for hvordan å opprette disse, i tillegg til å finne assets og lyd, som bilder av karakterer til animasjon, spillkart og lydeffekter. Andrenevnte skulle ha ansvar for kjernelogikken i spillet, som hvordan å bevege spillkarakteren og skyte våpen. Sistnevnte skulle ha ansvar for alt det grafiske, som menyer, karakteranimasjon og vfx. 


* Vi startet utviklingsprosessen med tanken på at disse skulle forbli til slutten, men vi fant kjapt ut at rollene måtte være mer dynamiske enn som så. Arbeidsmengde var en stor faktor, men også at det tross alt var bedre jo flere hadde kjennskap til hvordan f.eks. en merge request blir håndtert i forhold til eventuelle feil og merging til main. Vi endte prosjektet med at alle hadde en viss kjennskap til tilnærmet alle delene av programmet, og oppnådde en bedre fordeling av ansvar på grunn av det.


* Når det gjaldt prosjektmetodikken, så gjorde vi også noen endringer i løpet av prosessen. Vi startet med utgangspunkt i XP (Extreme Programming), men fant fort ut at denne metodikken krevde et enormt forarbeid som passet dårlig inn med vår utviklingsprosess. Vi bevegde oss derfor over til Kanban som vår hovedmetodikk, altså å aktivt bruke verktøy som issue-boards og skrive kode kontinuerlig. Denne tok oss gjennom resten av programmeringsprosessen og var det vi fullførte med.


* Gruppedynamikken og kommunikasjon var gjennomgående bra. Noen tok på seg mer ansvar enn andre, ofte på grunn av at flere hadde obligasjoner på siden og mindre tid til å bruke på prosjektet. Dette førte til tider til en ubalansert commit-logg. Likevel hadde vi en stort sett god gruppedynamikk og ingen uenigheter som ikke kunne løses med mer en kjapp samtale. Alle på gruppen la inn en like stor innsats selv om loggen kanskje viser en annen historie. Vi hadde også kommunikasjonskanaler på både snapchat og discord, med jevnlige møter og oppdateringer slik at det var god flyt av ideer gjennom semesteret.


* Prosjektstrukturen var en som var i stadig endring. Vi begynte først med en grov outline av hvordan vi skulle strukturere koden i forhold til MVC-prinsipp, men de spesifikke implementasjonene av klasser, grensesnitt og abstraksjoner ble fort endret på. Vi burde nok tatt mer tid i starten til å definere klare grenser på hva vi skulle implementere, da vi hadde sluppet en del refaktoreringer senere, men samtidig var det vanskelig å anslå prosjektets omfang så tidlig i prosessen. Dette viser forbedringspotensial og er noe vi tar med oss i framtidige prosjekter. Den endelige prosjektstrukturen var en som fulgte prinsippene vi la opp mot og lett kan utvides.


* Når det gjelder hvordan hele prosjektet gikk, så kan vi peke til mange ting vi gjorde bra og mange ting vi kunne gjort bedre. På den gode siden, så har vi skrevet gjennomgående god kode, med fokus på å abstrahere bort detaljer og rom for å enkelt legge til ny funksjonalitet. Vi har hatt relativt jevnlige møter og hatt god kommunikasjon. Git-bruk har vært solid gjennom prosessen, og flere av oss har fått ekstremt viktig kjennskap til hvordan å f.eks. løse en konflikt mellom lokale branches og main. Det grafiske i spillet er vi også svært fornøyde med. På den andre siden, så ser vi absolutt forbedringspotensial i å jobbe jevnt istedenfor en siste innspurt metodikk som vi ofte falt for nå. Tross mange møter, så var vi også glemske med å skrive møtereferat, noe som kunne gjort at flere hadde falt ut av loopen dersom kommunikasjonen ikke ellers hadde vært god. Formatering av dokument kunne også vært bedre, i tillegg til bedre referering til assets. Det største vi har anerkjent at vi kunne blitt bedre på er mer jevnlig testing underveis. Vi unnlot å skrive noen særlige tester før innlevering 3, ofte på grunn av at refaktoreringer gjorde at disse feilet og ikke kunne blitt merget inn i main. Dette er et kombinert problem med det at vi ikke tok oss nok tid til å faktisk legge opp en god plan for implementasjon tidlig i prosessen. Tross at det har vært opp- og nedturer gjennom prosjektet, så er sluttresultatet et spill vi er svært stolt av.


## Krav og spesifikasjon
*	Gjennom bruk av Kanban i prosjektet, så har kravene vi prioriterte vært lagt opp i issue boards. Dette gjorde at vi lett kunne skrive opp nye ting vi ville fikse eller legge til, samtidig som det var lett å delegere ansvar for oppgavene. I starten av prosjektet var prioriteten på å få et MVP så tidlig som mulig, der det viktigste var å få en karakter på skjermen som kunne bevege seg og skyte prosjektiler. Å jobbe videre fra dette innebar å sette opp konkrete krav, delegere kravet til en eller flere på gruppen, lage en branch for å utvikle kravet på, legge inn merge request når kravet ble ferdig og å stenge kravet i issue boards.


* Siden første innlevering med sitt MVP, har vi jobbet mot å få et fullverdig produkt, og vi har nå et grafisk interessant spill med involverte spillmekanikker som jobber sammen for å lage en gøy opplevelse for brukeren. Det har skjedd endringer underveis som har gjort at vår opprinnelige MVP har måttet byttes på, som at tiden ikke strakk til funksjonaliteten vi ønsket, men kjerneprinsippene er fortsatt like. For kravene våre prioriterte vi kvalitet over kvantitet. Noen bugs gjenstår, som at det er mulig å klippe gjennom plattformer når vinduet resizes, men koden er for det meste bug-fri. 

## Kode
*	Som diskutert i de øvrige avsnittene, så har koden vår gått gjennom en prosess med mye refaktorering og endringer. Årsaken var en uklar implementasjon tidlig i prosessen slik at det fort ble lekkasjer mellom ulike filer. De viktigste refaktoreringene vi gjorde i prosjektet var å tette lekkasjer med tanke på MVC ved å blant annet flytte mye feilplassert logikk fra AppController til AppWorld, endre strukturen på våpenklasser for å sikre bedre samarbeid mellom fabrikkene, endringer i kollisjonslogikk og å korrekt abstrahere bort logikk, spesielt ved entities. Utenom dette var endringene og deres omfang små nok til at det ikke var behov for særlige refaktoreringer.


* Arkitekturen nå følger en ren MVC-stil, med klare skiller mellom modell, kontroll og view. I tillegg har den modulære kodestilen vår med grensesnitt og abstrakte klasser gjort det enkelt å legge til videre funksjonalitet. Ser vi f.eks. på våpen, så trenger vi kun legge til en enkelt klasse med noen faste verdier, og bruke de allerede eksisterende abstrakte klassene for å fullføre implementasjonen. De ulike klassene er altså tett knyttet, noe som gjør det lett å få ting til å henge sammen.


* Kodekvalitet- og stil har vært god, selv om vi er en gruppe bestående av mennesker med ulik kompetanse innenfor både programmeringsspråket og samarbeid via git. Det kan til tider være vanskelig å tolke en kodebit viss man ikke selv har skrevet den, men god dokumentasjon og kommunikasjon innad gruppen har sikret at selv disse blir forklart og utdypet om nødvendig. Siden prosjektet er helt nytt, har vi ikke sett oss nødt til å vedlikeholde koden enda, men klare parametre og god kodemetodikk gjør at dette er sikret.


* Testing gikk tregt i starten, men etter innlevering 3 skjøt vi fart i å teste tilnærmet alle metodene i modellen og kontrolleren. Vi testet ikke view, da god separasjon mellom denne sørget for at ingen avhengigheter eksisterte og testing bestod da av å inspisere de visuelle elementene fra når spillet ble kjørt. Dette endte med at vi fikk en total coverage, utenom view, som lå godt innenfor kravene i oppgaven.


## Konklusjon
*	Gjennom prosjektet har vi lært en rekke ulike ting og gjort oss mange viktige erfaringer. Ting vi trodde skulle være lett å implementere viste seg å være svært tidskrevende, mens andre ting var lekende lett. Gjennom et semester med hardt arbeid og lange timer har vi nå lagt fram et fullstendig spill, som, tross at det trolig ikke ville vært en braksuksess i det frie marked, er det et endelig produkt som følger prinsippene vi ønsket og vi kan si oss godt fornøyde med. Spillet selv er likevel ikke den viktigste erfaringen, men heller hvordan det er å jobbe i en større gruppe på et slikt prosjekt og hvilke ting som blir lettere og vanskeligere i en slik setting. For å konkludere, så har vi fått en større innsikt i systemutvikling og hva det innebærer, slik at vi nå kan tre fram i fremtidige prosjekt med en kjennskap til hvordan vi skal løse et hvilket problem vi møter på.
