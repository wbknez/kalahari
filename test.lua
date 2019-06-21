local bounds = khlua.bindClass("com.solsticesquared.kalahari.math.Bounds")
local color = khlua.bindClass("com.solsticesquared.kalahari.math.color.Color3")
local image = khlua.bindClass("com.solsticesquared.kalahari.render.ImageOutput")
local order = khlua.bindClass("com.solsticesquared.kalahari.render.order.NaturalDrawingOrder")
local pipeline = khlua.bindClass("com.solsticesquared.kalahari.render.Pipeline")
local point3 = khlua.bindClass("com.solsticesquared.kalahari.math.Point3")
local sphere = khlua.bindClass("com.solsticesquared.kalahari.math.volume.BoundingSphere")
local tracer = khlua.bindClass("com.solsticesquared.kalahari.render.Tracer")

-- Variables.
local nord = khlua.newInstance("NaturalDrawingOrder")

local pipe = khlua.newInstance("Pipeline")

local view = khlua.newInstance("Bounds", 500, 500)

local out = khlua.newInstance("ImageOutput", "test", image.Format.PNG)

-- Colors
local red = khlua.newInstance("Color3", 1.0, 0.0, 0.0)

local orange = khlua.newInstance("Color3", 1.0, 0.70, 0.0)

local yellow = khlua.newInstance("Color3", 1.0, 1.0, 0.0)

local green = khlua.newInstance("Color3", 0.0, 0.70, 0.0)

local blue = khlua.newInstance("Color3", 0.0, 0.0, 1.0)

local white = khlua.newInstance("Color3", 1.0, 1.0, 1.0)

-- Bounding spheres.
local spRed = khlua.newInstance(
    "com.solsticesquared.kalahari.math.volume.BoundingSphere",
    101.0, 150.0, 150.0, 0.0
)

local spYellow = khlua.newInstance(
    "com.solsticesquared.kalahari.math.volume.BoundingSphere",
    101.0, 250.0, 250.0, 0.0
)

local spBlue = khlua.newInstance(
    "com.solsticesquared.kalahari.math.volume.BoundingSphere",
    101.0, 350.0, 350.0, 0.0
)

local trace_it = khlua.createProxy("com.solsticesquared.kalahari.render.Tracer", {
    trace = function(coords)
        local x = coords:getX()
        local y = coords:getY()
        local p = khlua.newInstance(
            "com.solsticesquared.kalahari.math.Point3",
            x, y, 0.0
        )

        -- Primary colors.
        local inRed    = spRed:contains(p)
        local inYellow = spYellow:contains(p)
        local inBlue   = spBlue:contains(p)

        -- Secondary colors.
        local inOrange = inRed and inYellow
        local inGreen  = inYellow and inBlue

        if inOrange then
            return orange
        elseif inGreen then
            return green
        elseif inRed then
            return red
        elseif inYellow then
            return yellow
        elseif inBlue then
            return blue
        end

        return white
    end
})

pipe:addListener(out)
pipe:run(trace_it, view, nord)
