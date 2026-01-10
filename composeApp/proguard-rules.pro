# Repackage classes into the top-level.
-repackageclasses

# Amount of optimization iterations, taken from an SO post
-optimizationpasses 5
-mergeinterfacesaggressively

# Broaden access modifiers to increase results during optimization
-allowaccessmodification

# Suppress missing class warnings
-dontwarn org.slf4j.impl.StaticLoggerBinder
