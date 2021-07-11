# Phase 1 – Graphical primitives
This phase requires two applications: one to generate files with the models information (in this phase only generate the vertices for the model) and the engine itself which will read a configuration file, written in XML, and display the models. To create the model files an application (independent from the engine) will receive as parameters the graphical primitive’s type, other parameters required for the model creation, and the destination file where the vertices will be stored.

In this phase the following graphical primitives are required:
* Plane (a square in the XZ plane, centred in the origin, made with 2 triangles)
* Box (requires X, Y and Z dimensions, and optionally the number of divisions)
* Sphere (requires radius, slices and stacks)
* Cone (requires bottom radius, height, slices and stacks)