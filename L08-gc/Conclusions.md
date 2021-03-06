|  Garbage collector      | Number of collects | Average collection time, ms
| ----------------------- | ------------------ | -------------------------- |
| **SerialGC**            | 139                | 181                        |
| **ParallelGC**          | 206                | 175                        |
| **G1GC**                | 532                | 32                         |

SerialGC эффективен при работе с небольшими короткоживущими объектами.
Создавался для использования на одно-процессорной платформе и малоэффективен
при мультитрединге.

ParallelGC, по моим ощущениям, не дает сильного прироста в производительности
относительно SerialGC, но более эффективен при использовании на много-процессорной
архитектуре.

G1GC лучше всего себя показывает при использовании большого объема памяти,
позволяя более эффективно ее использовать.


При использовании G1GC значительный выигрыш по времени STW, минорные сборки происходят чаще, но они значительно короче.
С другой стороны, SerialGC и ParallelGC запускают сборку реже, но STW занимает гораздо большее время.
180 мс у SerialGC/ParallelGC против 30 мс у G1GC.
При этом фаза Pause Full (G1 Evacuation Pause) занимает сопоставимое с SerialGC/ParallelGC время (190 мс).

Если время STW для нас критично, то следует использовать G1GC. Однако надо учитывать, что при приближении занимаемого объема памяти к Xmx
время работы Pause Full (G1 Evacuation Pause) сопоставимо с временем STW для SerialGC/ParallelGC.