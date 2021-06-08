# Kappa

A Fabric mod that enables OptiFine's capes on snapshots.
Licensed under Unlicense.
Made by Hibi.

![Kappa screenshot in latest snapshot](.github/kappa_screenshot.png)

Usually Fabric mods are compatible between versions, but sometimes, like in the 1.17 snapshots, Minecraft itself breaks the mods.
I will test my mod against the latest snapshot, because I like playing with my OptiFine cape, so if my mod is broken, I will update it.
When a version of Kappa is compatible with the newest snapshot, I will not rerelease it, but I'll certify it for working in said snapshot.
You will know you when to update the mod when it's broken in the latest snapshot.

The difference between [Drago's Capes][1] and even [Colher's Capes][2] is that Kappa does not register its own cape and elytra features.
Effectively what those do is *duplicating* the cape, they *clone* the cape code (which is more work to maintain).
However, I want capes in snapshots, so the smallest codebase is the best because it's the easier to maintain.
Effectively what Kappa does is inject OptiFine capes into the game: any mods that affect the vanilla cape will also affect the OptiFine cape.

Yes, OptiFine's cape breaks Minecraft's commercial usage guidelines, but I already had it for lots longer. I consider it part of my skin.

---

Source: https://github.com/Hibiii/Kappa

[1]: https://www.curseforge.com/minecraft/mc-mods/of-capes
[2]: https://www.curseforge.com/minecraft/mc-mods/capes
