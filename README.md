## TST Programming Exercises

### by Jared Hooper ([Github](https://github.com/jredh))

---

### Solution #1

#### Notes:

This one went by quickly. I think I could probably provide a few more test cases to make sure the code is covered.

```bash
$> solution-1.sh

2020.11.12 22:25:39 [INFO] j.g.C.main:33 - BestGroupPrice(CA,M1,200.0,Military)
2020.11.12 22:25:39 [INFO] j.g.C.main:33 - BestGroupPrice(CA,S1,225.0,Senior)
2020.11.12 22:25:39 [INFO] j.g.C.main:33 - BestGroupPrice(CB,M1,230.0,Military)
2020.11.12 22:25:39 [INFO] j.g.C.main:33 - BestGroupPrice(CB,S1,245.0,Senior)
```

### Solution #2

#### Notes:

I cheated a bit by using `Set`s rather than doing a slightly harder uniqueness check. By switching between `Set` and `Seq` I ended up with `Vectors` at the end. I hope you can forgive me.

```
$> solution-2.sh

2020.11.13 14:17:24 [INFO] j.p.C.main:15 - Vector(PromotionCombo(Vector(P1, P2)), PromotionCombo(Vector(P2, P3)), PromotionCombo(Vector(P1, P4, P5)), PromotionCombo(Vector(P3, P4, P5)))
2020.11.13 14:17:24 [INFO] j.p.C.main:19 - Vector(PromotionCombo(Vector(P1, P2)), PromotionCombo(Vector(P1, P4, P5)))
2020.11.13 14:17:24 [INFO] j.p.C.main:24 - Vector(PromotionCombo(Vector(P2, P3)), PromotionCombo(Vector(P3, P4, P5)))
```

---

## Tests / General Notes

- There are two main projects, `promocombo` and `grouprate`. They consolidate in a `global` project.
- You can run tests for the `global` project to see everything that I was running
- There are debug logs that I've silenced.