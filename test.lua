local bounds = luajava.bindClass("com.solsticesquared.kalahari.math.Bounds")
local color = luajava.bindClass("com.solsticesquared.kalahari.math.color.Color3")
local image = luajava.bindClass("com.solsticesquared.kalahari.render.ImageOutput")
local order = luajava.bindClass("com.solsticesquared.kalahari.render.order.NaturalDrawingOrder")
local pipeline = luajava.bindClass("com.solsticesquared.kalahari.render.Pipeline")
local point3 = luajava.bindClass("com.solsticesquared.kalahari.math.Point3")
local sphere = luajava.bindClass("com.solsticesquared.kalahari.math.volume.BoundingSphere")
local tracer = luajava.bindClass("com.solsticesquared.kalahari.render.Tracer")

-- Variables.
local nord = luajava.newInstance(
    "com.solsticesquared.kalahari.render.order.NaturalDrawingOrder"
)

local out = luajava.newInstance(
    "com.solsticesquared.kalahari.render.ImageOutput",
    "test", image.Format.PNG
)

local pipe = luajava.newInstance(
    "com.solsticesquared.kalahari.render.Pipeline"
)

local view = luajava.newInstance(
    "com.solsticesquared.kalahari.math.Bounds",
    500, 500
)

-- Colors
local red = luajava.newInstance(
    "com.solsticesquared.kalahari.math.color.Color3",
    1.0, 0.0, 0.0
)

local orange = luajava.newInstance(
        "com.solsticesquared.kalahari.math.color.Color3",
        1.0, 0.70, 0.0
)

local yellow = luajava.newInstance(
        "com.solsticesquared.kalahari.math.color.Color3",
        1.0, 1.0, 0.0
)

local green = luajava.newInstance(
        "com.solsticesquared.kalahari.math.color.Color3",
        0.0, 0.70, 0.0
)

local blue = luajava.newInstance(
        "com.solsticesquared.kalahari.math.color.Color3",
        0.0, 0.0, 1.0
)

local white = luajava.newInstance(
        "com.solsticesquared.kalahari.math.color.Color3",
        1.0, 1.0, 1.0
)

-- Bounding spheres.
local spRed = luajava.newInstance(
        "com.solsticesquared.kalahari.math.volume.BoundingSphere",
        101.0, 150.0, 150.0, 0.0
)

local spYellow = luajava.newInstance(
        "com.solsticesquared.kalahari.math.volume.BoundingSphere",
        101.0, 250.0, 250.0, 0.0
)

local spBlue = luajava.newInstance(
        "com.solsticesquared.kalahari.math.volume.BoundingSphere",
        101.0, 350.0, 350.0, 0.0
)

local trace_it = luajava.createProxy("com.solsticesquared.kalahari.render.Tracer", {
    trace = function(coords)
        local x = coords:getX()
        local y = coords:getY()
        local p = luajava.newInstance(
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
