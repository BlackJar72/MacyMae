Graphics go in, or under, this package.

Ideally, images should be sorted, especially frames of the same animation, into
sub-packages/sub-directories.  In the latter case it would facilitate the use
of a loading system that could put all images in the same location into one
Graphic object rather, which would be better than relying on hard-coded lists
that I could forget to update.

Other types of graphics could still be sorted similarly by tile type and just 
not loaded with the same method.