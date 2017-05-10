#Klienci i serwery 2


Serwer obsługuje rozsyłanie wiadomości do klientów. Każdy klient może zapisać się do 
świadczonej usługi podając tematy, którymi się interesuje (polityka, sport, celebryci,
 gotowanie, randki, ...). Osobnym programem przesyłamy do serwera wiadomości z 
 różnych dziedzin, a on rozsyła je do subskrybentów zainteresowanych danym tematem. 
 Tym samym programem zarządzamy tematami. Do obsługi połączeń 
 (typu "subscribe", "unsubscribe", oraz połączeń przysyłających nowe wiadomości do rozesłania)
  użyj selektorów - nie twórz nowych wątków!

Stwórz proste GUI (separujące od logiki przetwarzania danych). Aplikacja powinna być odporna na różne sytuacje awaryjne.